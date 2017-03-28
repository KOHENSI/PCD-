package com.crawler;

import java.util.ArrayList;
import java.util.List;

import com.entity.Product;
import com.ui4j.api.browser.Page;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;

public class ScoopCrawler extends Crawler{

	public ScoopCrawler(){
		
		setName("scoop");
		setUrl("http://www.scoop.com.tn/");
		venderID=getVenderID();
		
		categorySelector = "div[class^='categorie'] > li > a ";
		productBlockSelector = "div[class='article']";
		nameSelector = "span[class='titre_desc']>a";
		priceSelector = "div:nth-child(4) > span[class='prix_article']";
		descSelector = "span[class='description']";
		linkSelector = "span[class='titre_desc']>a";
		imageLinkSelector = "a > img";
		paginationSelector = "ul[class='pagination'] > li > a";
		loadingContent = "loading.gif";
		
	}
	@Override
	protected void getCategoryLinks() {
		String link;
        
		Page page = webkit
                .navigate(venderUrl);
        Document document = page.getDocument();
        
        categoryElements = document.queryAll(categorySelector);
        
        for (int i =0 ; i < categoryElements.size() ; i++)
        {
        	element = categoryElements.get(i);
        	link = element.getAttribute("href").get();
        	if (!link.contains(venderUrl)) link=venderUrl+link;
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
	      
	        	//subPage.show(true);
	        	
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
	        	
	        		String pName;
	        	
		        	for(int j=0; j<products.size();j++)
		        	{
			        	product = products.get(j);
			    		
			    		
			        	//p.setBrand(
			    		//		product.query(BrandSelector).get().getInnerHTML()
			    		//);
			        	
			        	pName = product.query(nameSelector).get().getInnerHTML();
			    		
			    		p.setBrand(getBrandName(pName));
			    		
			    		p.setName(pName.toLowerCase().replaceFirst(p.getBrand(),"").trim());
			    		
			    		p.setCategory(
			    				categoryService.removeUselessKeys(
			    						categoryLinks.get(i).replaceFirst(venderUrl, "")
			    				)
			    		);
			    		System.out.println(p.getCategory());
			    		
			    		p.setCategoryID( categoryService.lookForCategoryV2(p.getCategory()));
			        	
			        	p.setPrice(
			    				Integer.parseInt( 
			    						product.query(priceSelector).get().getInnerHTML().replaceAll("<[^>]*>"," ").replaceAll("[^0-9]*","").trim()
			    				)
			    		);
			    		p.setDesc(
			    				product.query(descSelector).get().getInnerHTML().replaceAll("(<br>)|-"," ").replaceAll("<[^>]*>|[,;]"," ").replaceAll("\\s+"," ")
			    		);
			    		
			    		pLink = product.query(linkSelector).get().getAttribute("href").get();
			    		if (!pLink.contains(venderUrl)) pLink=venderUrl+pLink;
			    		p.setLink(pLink);
			    		
			    		pLink = product.query(imageLinkSelector).get().getAttribute("src").get();
			    		if (!pLink.contains(venderUrl)) pLink=venderUrl+pLink;
			    		p.setImgaeLink(pLink);
			    		
			    		saveProduct(p);
		        	}
	        	}
	        	//subPage.close();
        	}
		
	}
	
	private String getBrandName(String Source){
		return Source.replaceFirst(" .*", "").trim().toLowerCase();
	};
	
	public static void main ( String[] args){
      		ScoopCrawler c = new ScoopCrawler();
      		c.crawl();
      		c.Stop();
     }
}
