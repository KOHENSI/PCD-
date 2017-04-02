package com.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.entity.Product;
import com.ui4j.api.browser.Page;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;

public class ScoopCrawler2 extends Crawler{

	public ScoopCrawler2(){
		
		setName("scoop");
		setUrl("http://www.scoop.com.tn/");
		venderID=getVenderID();
		
		categorySelector = "div[class^='categorie'] > li > a ";
		mainCategorySelector = "li[class='has-sub']";
		subCategorySelector = "div[class^='categorie'] > li > a";
		productBlockSelector = "div[class='article']";
		nameSelector = "span[id='title']";
		priceSelector = "span[class='prix_article']";
		descSelector = "span[itemprop='description']";
		brandSelector = "span[itemprop='brand']";
		referenceSelector = "span[id='ref']";
		linkSelector = "span[class='titre_desc']>a";
		imageLinkSelector = "a[class='ajax cboxElement']";
		paginationSelector = "ul[class='pagination'] > li > a";
		categoryNameSelector ="span:nth-last-child(1) > a[class='lien_bread'] > span";
		loadingContent = "loading.gif";
		
		categoriesToIgnore.add(6);
		categoriesToIgnore.add(5);
	}
	
	@Override
	protected void getProductLinks()
	{   
        for(int i = 0; i<categoryLinks.size();i++)
        {
        	subPage = webkit
                    .navigate(categoryLinks.get(i));
            subDocument = subPage.getDocument();
            
        	int tryOutNumber = 0;
        	int maxTryOutNumber = 10;
        	
        	//In case the page does not exist
        	if (!subDocument.query("div[id='liste_prod']").isPresent()) continue;
        	
        	if (loadingContent != "" )
	        	do
	        	{
	        		waitNSecond(1/2);
	        	}
	        	while( tryOutNumber <= maxTryOutNumber && subDocument.query("div[id='liste_prod']").get().getInnerHTML().contains(loadingContent) )	;
	        	
        	int pageNumbers = 1;
        	Element paginationNav = null;
        	List<Element> pp = new ArrayList<Element>();
        	
        	if (paginationSelector != "" );
        	{
        		pp = subDocument.queryAll(paginationSelector);
        		pageNumbers = pp.size();
        		//System.out.println(pageNumbers);
        	}
     
        	for ( int h = 0; h < pageNumbers ; h++ )
	        {
        	
        		if (paginationSelector != "" );
	        	{
	        		paginationNav = pp.get(h);
	        	}
	        	
	        	//document.trigger("click", paginationNav);
	        	paginationNav.click();
	        	
	        	tryOutNumber = 0;
	        	if (loadingContent != "" )
		        	do
		        	{
		        		waitNSecond(1/2);
		        	}
		        	
		        	while( tryOutNumber <= maxTryOutNumber && subDocument.query("div[id='liste_prod']").get().getInnerHTML().contains(loadingContent) )	;
		        	
        	
        		products = subDocument.queryAll(productBlockSelector);
        		
        		//System.out.println(products.size());
        	
	        	for(int j=0; j<products.size();j++)
	        	{
	        		Optional<Element> aux = products.get(j).query(linkSelector);
		    		if (!aux.isPresent())continue;
		    		
		    		String l = aux.get().getAttribute("href").get();
		    		
		    		if (!l.contains(venderUrl)) l=venderUrl+l;
		    		
        			if (!productLinks.contains(l))
        				productLinks.add(l);
        			System.out.println(l);
	        	}
	        }	
        }
	}
	
	@Override
	protected void getProducts() {
		
		String ref,category,nom,prix,desc,pLink,pImgLink,brand;
        Product p = new Product();
        
        for (int i =0 ; i < productLinks.size() ; i++)
        {
	        	
        	subPage = webkit.navigate(productLinks.get(i));
        	
        	subDocument = subPage.getDocument();
      
        	//try{
				category = subDocument.query(categoryNameSelector).get().getInnerHTML();
				ref = subDocument.query(referenceSelector).get().getInnerHTML();
	    		prix = subDocument.query(priceSelector).get().getInnerHTML();
	    		brand = subDocument.query(brandSelector).get().getInnerHTML();
	    		nom = subDocument.query(nameSelector).get().getInnerHTML();
	    		desc = subDocument.query(descSelector).get().getInnerHTML();
	    		pLink = productLinks.get(i);
	    		pImgLink = subDocument.query(imageLinkSelector).get().getAttribute("href").get();
	    		//pName = subDocument.query(nameSelector).get().getInnerHTML();
			/*}catch (Exception e) {
				System.out.println("PROBLEM ENCOUTRED*********************************");
				continue;
			}*/
			
	    	ref=ref.replaceAll("<.*","").trim();
	    		
        	p.setRef(ref);
			
    		p.setBrand(brand);
    		
    		//nom = nom.toLowerCase().replace(p.getBrand(), "").replace(p.getCategory(), "");
    		
    		p.setName(nom);
        	
    		category= category.toLowerCase().replaceAll(p.getName(),"").replaceAll(p.getBrand().toLowerCase(),"");
    		System.out.println(category);
    		p.setCategory(category);
    		
    		p.setPrice(Integer.parseInt(prix.replaceAll(",.*| +", "").replaceAll("[^0-9]", "")));
    		p.setDesc(desc.replaceAll("(<br>)|-"," ").replaceAll("<[^>]*>"," ").replaceAll("\\s+"," "));
    		
    		if (!pLink.contains(venderUrl)) pLink=venderUrl+pLink;
    		p.setLink(pLink);
    		
    		pLink = pImgLink;
    		if (!pLink.contains(venderUrl)) pLink=venderUrl+pLink;
    		p.setImgaeLink(pLink);
    		
    		System.out.println(p.getCategory() +" "+ p.getBrand() +" " + p.getRef() 
    		+ " "+ p.getName() +" "+p.getPrice() +" "+ p.getDesc() +" "+ p.getImgaeLink() +" "+
    				p.getLink());
    		
    		saveProduct(p);
	         	
        }
		
	}
	
	public static void main ( String[] args){
      		ScoopCrawler2 c = new ScoopCrawler2();
      		//c.getCategoryLinks();
      		//c.getProductLinks();
      		//c.productLinks.add("http://www.scoop.com.tn/1-8613-46-pc-portables-asus-e202sa-fd0114d");
      		//c.getProducts();
      		c.crawl();
      		c.Stop();
     }
}
