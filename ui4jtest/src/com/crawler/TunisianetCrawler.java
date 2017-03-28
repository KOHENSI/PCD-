package com.crawler;

import java.util.ArrayList;
import java.util.List;

import com.entity.Product;
import com.ui4j.api.browser.Page;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;

public class TunisianetCrawler extends Crawler{
	public TunisianetCrawler(){
		
		setName("tunisianet");
		setUrl("http://www.tunisianet.com.tn/");
		venderID=getVenderID();
		
		brandSelector="div[id='produit_liste_marque'] > a > img";
		categoryNameSelector = "div[id='catalogue_header_title'] > h1";
		categorySelector = "div[class^='adtm_column adtm_column_'] > h5 > a";
		productBlockSelector = "li[class^='ajax_block_product']";
		nameSelector = "div[id='produit_liste_texte'] > div > h2 > a";
		priceSelector = "span[class='price']";
		descSelector = "div[id='produit_liste_texte'] > div > p > a";
		linkSelector = "div[id='produit_liste_texte'] > div > h2 > a";
		imageLinkSelector = "div[id='produit_liste_img'] img";
	}
	@Override
	protected void getCategoryLinks() {
		
		Page page = webkit
                .navigate(venderUrl);
        Document document = page.getDocument();

        categoryElements = document.queryAll(categorySelector);
      
        Element element;
        String link;
        
        for (int i =0 ; i < categoryElements.size() ; i++)
        {
        	element = categoryElements.get(i);
        	link = element.getAttribute("href").get();
        	categoryLinks.add(link);
        	System.out.println(link);
        }
        
        
		
	}
	@Override
	protected void getProducts() {
		
		String pLink ;
        Product p = new Product();
        
        for (int i =0 ; i < categoryLinks.size() ; i++)
        {
        	subPage = webkit.navigate(categoryLinks.get(i));
        	subDocument = subPage.getDocument();
        	
        	//subPage.show();
        	
        	products = subDocument.queryAll(productBlockSelector);
        	

    		String category,pName,nom,prix,desc,plink,pImgLink,brand;
    		
    		category= subDocument.query(categoryNameSelector).get().getInnerHTML().replaceFirst("<.*", "").toLowerCase().trim();
    		//category= subDocument.query(categoryNameSelector).get().getAttribute("value").get().toLowerCase().trim();
        	p.setCategory(category);
        	p.setCategoryID(categoryService.lookForCategoryV2(category));
        	
    		for(int j=0; j<products.size();j++)
        	{
	        	product = products.get(j);
	    		prix = product.query(priceSelector).get().getInnerHTML();
	    		brand = product.query(brandSelector).get().getAttribute("title").get();
	    		nom = product.query(nameSelector).get().getInnerHTML();
	    		desc = product.query(descSelector).get().getInnerHTML();
	    		pLink = product.query(linkSelector).get().getAttribute("href").get();
	    		pImgLink = product.query(imageLinkSelector).get().getAttribute("src").get();
	    		
	    		pName = product.query(nameSelector).get().getInnerHTML();
	    		
	    		p.setBrand(brand);
	    		
	    		nom = nom.toLowerCase().replace(p.getBrand(), "").replace(p.getCategory(), "");
	    		
	    		p.setName(nom);
	        	
	        	p.setPrice(Integer.parseInt(prix.replaceAll(",.*| +", "")));
	    		p.setDesc(desc.replaceAll("(<br>)|-"," ").replaceAll("<[^>]*>|[,;]"," ").replaceAll("\\s+"," "));
	    		
	    		if (!pLink.contains(venderUrl)) pLink=venderUrl+pLink;
	    		p.setLink(pLink);
	    		
	    		pLink = pImgLink;
	    		if (!pLink.contains(venderUrl)) pLink=venderUrl+pLink;
	    		p.setImgaeLink(pLink);
	    		
	    		saveProduct(p);
	       	}
        }
	}
	
	public static void main ( String[] args){
      		TunisianetCrawler c = new TunisianetCrawler();
      		//c.getCategoryLinks();
      		c.crawl();
      		c.Stop();
     }

}
