package com.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.entity.Product;
import com.ui4j.api.dom.Element;

public class MytekCrawler extends Crawler{
	
	MytekCrawler()
	{
		setName("mytek");
		setUrl("http://www.mytek.tn/");
		venderID=getVenderID();
		categoriesToIgnore.add(0);
		
		categoriesToIgnore.add(5);
		categoriesToIgnore.add(6);
		categoriesToIgnore.add(7);
		categoriesToIgnore.add(8);
		categoriesToIgnore.add(9);
		categoriesToIgnore.add(10);
		categoriesToIgnore.add(11);
		categoriesToIgnore.add(12);
		
		mainCategorySelector ="li[class^='li-niveau1']";
		subCategorySelector ="span[class^='column_wrap_title'] > a";
		brandSelector="div[id='produit_liste_marque'] > a > img";
		categoryNameSelector = "span[class='navigation_page'] > span:nth-last-child(2)";
		categorySelector = "span[class='navigation_page']";
		productBlockSelector = "div[class='product-container']";
		nameSelector = "h1[itemprop='name']";
		priceSelector = "span[id='our_price_display']";
		descSelector = "div[id='short_description_content'] > p";
		imageLinkSelector = "img[id='bigpic']";
		brandSelector = "div[class='manu'] > p > a";
		loadingContent = "http://www.mytek.tn/img/loader.gif";
		paginationSelector = "ul[class='pagination'] li";
		referenceSelector = "p[id='product_reference'] > span";
		//ul[class^='product_list_grid']
	}

	@Override
	protected void getProductLinks() {
		
		 for (int i =0 ; i < categoryLinks.size() ; i++)
	        {
		        	
		        	subPage = webkit.navigate(categoryLinks.get(i));
		        	
		        	subDocument = subPage.getDocument();
		        	
		        	int tryOutNumber = 0;
		        	int maxTryOutNumber = 10;
		        	
		        	//In case the page does not exist
		        	if (!subDocument.query("ul[class='product_list grid ']").isPresent()) continue;
		        	
		        	if (loadingContent != "" )
			        	do
			        	{
			        		waitNSecond(1/2);
			        	}
			        	while( tryOutNumber <= maxTryOutNumber && subDocument.query("ul[class='product_list grid ']").get().getInnerHTML().contains(loadingContent) )	;
			        	
		        	Optional<Element> showAllButton;
		        	String ShowAllButtonSelector = "form[class='showall'] button";
		        	
		        	showAllButton = subDocument.query(ShowAllButtonSelector);
		        	
		        	if(showAllButton.isPresent()) showAllButton.get().click();
		     
		        	
			        	tryOutNumber = 0;
			        	if (loadingContent != "" )
				        	do
				        	{
				        		waitNSecond(1/2);
				        	}
				        	
				        	while( tryOutNumber <= maxTryOutNumber && subDocument.query("ul[class='product_list grid ']").get().getInnerHTML().contains(loadingContent) )	;
				        	

		        		String prix,brand,nom,desc,Link,pName,pImgLink,catName,ref;
		        		
		        		products = subDocument.queryAll(productBlockSelector +" a[class='product_img_link']");
		        		
		        		for (int j=0 ; j<products.size();j++)
		        		{
		        			String l = products.get(j).getAttribute("href").get();
		        			if (!productLinks.contains(l))
		        				productLinks.add(l);
		        		}
	
	        }
	}
	
	@Override
	protected void getProducts() {
		String pLink ;
        Product p = new Product();
        
   	        		
		for(int j=0; j<productLinks.size();j++)
    	{
			System.out.println(productLinks.get(j));
			subPage = webkit.navigate(productLinks.get(j));
			subDocument = subPage.getDocument();
			
			String prix,brand,nom,desc,Link,pName,pImgLink,catName,ref;
			
			try{
				catName = subDocument.query(categoryNameSelector).get().getInnerHTML();
				ref = subDocument.query(referenceSelector).get().getInnerHTML();
	    		prix = subDocument.query(priceSelector).get().getInnerHTML();
	    		brand = subDocument.query(brandSelector).get().getInnerHTML();
	    		nom = subDocument.query(nameSelector).get().getInnerHTML();
	    		desc = subDocument.query(descSelector).get().getInnerHTML();
	    		Link = productLinks.get(j);
	    		pImgLink = subDocument.query(imageLinkSelector).get().getAttribute("src").get();
	    		//pName = subDocument.query(nameSelector).get().getInnerHTML();
			}catch (Exception e) {
				System.out.println("PROBLEM ENCOUTRED*********************************");
				continue;
			}
			
    		catName= catName.toLowerCase().replaceAll("<[^>]*>[^<]*<[^>]*>", "");
    		System.out.println(catName);
    		p.setCategory(catName);
			
        	p.setRef(ref);
			
    		p.setBrand(brand);
    		
    		//nom = nom.toLowerCase().replace(p.getBrand(), "").replace(p.getCategory(), "");
    		
    		p.setName(nom);
        	
        	p.setPrice(Integer.parseInt(prix.replaceAll(",.*| +", "")));
    		p.setDesc(desc.replaceAll("(<br>)|-"," ").replaceAll("<[^>]*>"," ").replaceAll("\\s+"," "));
    		
    		if (!Link.contains(venderUrl)) Link=venderUrl+Link;
    		p.setLink(Link);
    		
    		Link = pImgLink;
    		if (!Link.contains(venderUrl)) Link=venderUrl+Link;
    		p.setImgaeLink(Link);
    		
    		System.out.println(p.getCategory() +" "+ p.getBrand() +" " + p.getRef() 
    		+ " "+ p.getName() +" "+p.getPrice() +" "+ p.getDesc() +" "+ p.getImgaeLink() +" "+
    				p.getLink());
    		
    		saveProduct(p);
       	}
	
	}
	
	public static void main(String[] args){
		MytekCrawler c = new MytekCrawler();
		//c.getCategoryLinks();
		//c.getProductLinks();
		c.crawl();
		c.Stop();
	}

}
