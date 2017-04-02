package com.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.entity.Product;
import com.ui4j.api.browser.Page;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;

public class TunisianetCrawler extends Crawler{


	public TunisianetCrawler(){
		
		setName("tunisianet");
		setUrl("http://www.tunisianet.com.tn/");
		venderID=getVenderID();
		
		categoriesToIgnore.add(6);
		categoriesToIgnore.add(8);
		categoriesToIgnore.add(9);
		categoriesToIgnore.add(10);
		categoriesToIgnore.add(11);
		
		mainCategorySelector ="ul[id='menu'] > li";
		subCategorySelector ="div[class^='adtm_column adtm_column_'] a";
		brandSelector="div[id='produit_liste_marque'] > a > img";
		categoryNameSelector = "div[id='catalogue_header_title'] > h1";
		categorySelector = "div[class^='adtm_column adtm_column_'] a";
		productBlockSelector = "li[class^='ajax_block_product']";
		nameSelector = "div[id='produit_liste_texte'] > div > h2 > a";
		priceSelector = "span[class='price']";
		descSelector = "div[id='produit_liste_texte'] > div > p > a";
		linkSelector = "div[id='produit_liste_texte'] > div > h2 > a";
		imageLinkSelector = "div[id='produit_liste_img'] img";
		referenceSelector = "span[style='font-size:10px; font-weight:normal']";
	}
	
	@Override
	protected void getProducts() {
		
		String pLink ;
        Product p = new Product();
        
        for (int i =categoryLinks.size()-1 ; i > 0 ; i--)
        {
        	subPage = webkit.navigate(categoryLinks.get(i));
        	subDocument = subPage.getDocument();
        	
        	//subPage.show();
        	
        	products = subDocument.queryAll(productBlockSelector);
        	

    		String ref,category,pName,nom,prix,desc,plink,pImgLink,brand;
    		
    		category= subDocument.query(categoryNameSelector).get().getInnerHTML().replaceFirst("<.*", "").toLowerCase().trim();
    		//category= subDocument.query(categoryNameSelector).get().getAttribute("value").get().toLowerCase().trim();
        	p.setCategory(category);
        	p.setCategoryID(categoryService.lookForCategoryV2(category));
        	
    		for(int j=0; j<products.size();j++)
        	{
    			try{
    				product = products.get(j);
    				ref = product.query(referenceSelector).get().getInnerHTML();
    	    		prix = product.query(priceSelector).get().getInnerHTML();
    	    		brand = product.query(brandSelector).get().getAttribute("title").get();
    	    		nom = product.query(nameSelector).get().getInnerHTML();
    	    		desc = product.query(descSelector).get().getInnerHTML();
    	    		pLink = product.query(linkSelector).get().getAttribute("href").get();
    	    		pImgLink = product.query(imageLinkSelector).get().getAttribute("src").get();
    	    		pName = product.query(nameSelector).get().getInnerHTML();
    			}catch (Exception e) {
					continue;
				}
    			
	        	ref= ref.replace("[", "").replace("]", "");
	        	p.setRef(ref);
    			
	    		p.setBrand(brand);
	    		
	    		//nom = nom.toLowerCase().replace(p.getBrand(), "").replace(p.getCategory(), "");
	    		p.setName(nom);
	    		
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
