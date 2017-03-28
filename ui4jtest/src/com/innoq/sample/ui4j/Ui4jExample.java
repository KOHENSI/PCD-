package com.innoq.sample.ui4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.BrowserFactory;
import com.ui4j.api.browser.BrowserType;
import com.ui4j.api.browser.Page;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;

public class Ui4jExample {

    private static BrowserEngine webkit = BrowserFactory.getBrowser(BrowserType.WebKit);

    public static void main(String[] args) {
    	
    	
    	//searchTuinianet();
    	searchScooptn();
    	//searchMyTek();
    	
    	/*
    	String url = "http://www.scoop.com.tn/";
    	String categorySelector = "div[class^='categorie'] a ";
    	String productBlockSelector = "div[class='article']";
    	String nameSelector = "span[class='titre_desc']>a";
    	String priceSelector = "div:nth-child(4) > span[class='prix_article']";
    	String descSelector = "span[class='description']";
    	String linkSelector = "span[class='titre_desc']>a";
    	String paginationSelector = "ul[class='pagination'] > li > a";
    	String loadingContent = "loading.gif";
    	
    	List<String> categoryLinks = new ArrayList<String>();
    	List<Element> categoryElements = new ArrayList<Element>();
    	
    	Element element;
        String link;
        
    	Page page = webkit
                .navigate(url);
        Document document = page.getDocument();
        
        categoryElements = document.queryAll(categorySelector);
        //categoryElements = document.queryAll("a[title='pc-portables Asus']");
        
        //page.show();
        
        for (int i =0 ; i < categoryElements.size() ; i++)
        {
        	element = categoryElements.get(i);
        	link = element.getAttribute("href").get();
        	if (!link.contains(url)) link=url+link;
        	categoryLinks.add(link);
        	System.out.println(link);
        }
        
        Document subDocument;
        Page subPage;
        List<Element> products = new ArrayList<Element>();
        Element product;
        String prix , desc , nom , pLink ;
        
        for (int i =0 ; i < categoryLinks.size() ; i++)
        {
	        	
	        	subPage = webkit.navigate(categoryLinks.get(i));
	        	
	        	subDocument = subPage.getDocument();
	      
	        	subPage.show(true);
	        	
	        	int tryOutNumber = 0;
	        	int maxTryOutNumber = 10;
	        	
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
		        	document.trigger("click", paginationNav);
	        		
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
			        	product = products.get(j);
			    		
			    		nom = product.query(nameSelector).get().getInnerHTML();
			    		prix = product.query(priceSelector).get().getInnerHTML().replaceAll("<[^>]*>"," ").replaceAll("\\s+"," ");
			    		desc = product.query(descSelector).get().getInnerHTML().replaceAll("<[^>]*>|[,;]"," ").replaceAll("\\s+"," ");;
			    		pLink = product.query(linkSelector).get().getAttribute("href").get();
			    		if (!pLink.contains(url)) pLink=url+pLink;
			    		System.out.println( nom + "\n" + prix  + "\n" + desc + "\n" + pLink + "\n");
		        	}
	        	}
	        	//subPage.close();
        	}/
        
        page.close();
    	webkit.shutdown();
    	/*
    	 * 
    	 */
    }
	
        
    private static void waitNSecond(int N) {
        try {
        	
            Thread.sleep(N*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void searchScooptn()
    {
    	String url = "http://www.scoop.com.tn/";
    	String categorySelector = "div[class^='categorie'] > li > a ";
    	String productBlockSelector = "div[class='article']";
    	String nameSelector = "span[class='titre_desc']>a";
    	String priceSelector = "div:nth-child(4) > span[class='prix_article']";
    	String descSelector = "span[class='description']";
    	String linkSelector = "span[class='titre_desc']>a";
    	String imageLinkSelector = "span[class='titre_desc']>a";
    	String paginationSelector = "ul[class='pagination'] > li > a";
    	String loadingContent = "loading.gif";
    	
    	List<String> categoryLinks = new ArrayList<String>();
    	List<Element> categoryElements = new ArrayList<Element>();
    	
    	Element element;
        String link;
        
    	Page page = webkit
                .navigate(url);
        Document document = page.getDocument();
        
        categoryElements = document.queryAll(categorySelector);
        //categoryElements = document.queryAll("a[title='pc-portables Asus']");
        
        //page.show();
        
        for (int i =0 ; i < categoryElements.size() ; i++)
        {
        	element = categoryElements.get(i);
        	link = element.getAttribute("href").get();
        	if (!link.contains(url)) link=url+link;
        	categoryLinks.add(link);
        	System.out.println(link);
        }
        
        Document subDocument;
        Page subPage;
        List<Element> products = new ArrayList<Element>();
        Element product;
        String prix , desc , nom , pLink ;
        
        
        emptyFile("scoop.txt");
        
        for (int i =0 ; i < categoryLinks.size() ; i++)
        {
	        	
	        	subPage = webkit.navigate(categoryLinks.get(i));
	        	
	        	subDocument = subPage.getDocument();
	      
	        	subPage.show(true);
	        	
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
		        	
		        	document.trigger("click", paginationNav);
		        	//paginationNav.click();
		        	
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
			        	product = products.get(j);
			    		
			    		nom = product.query(nameSelector).get().getInnerHTML();
			    		prix = product.query(priceSelector).get().getInnerHTML().replaceAll("<[^>]*>"," ").replaceAll("\\s+"," ");
			    		desc = product.query(descSelector).get().getInnerHTML().replaceAll("<br>","/").replaceAll("<[^>]*>|[,;]"," ").replaceAll("\\s+"," ");
			    		pLink = product.query(linkSelector).get().getAttribute("href").get();
			    		if (!pLink.contains(url)) pLink=url+pLink;
			    		saveSearchResults("scoop.txt",nom + "\r\n" + prix  + "\r\n" + desc + "\r\n" + pLink + "\r\n" );
			    		System.out.println( nom + "\n" + prix  + "\n" + desc + "\n" + pLink + "\n");
		        	}
	        	}
	        	//subPage.close();
        	}
		
        
        page.close();
    	webkit.shutdown();
    }
    
    public static void searchTuinianet()
    {
    	Page page = webkit
                .navigate("http://www.tunisianet.com.tn/");
        Document document = page.getDocument();

        List<Element> categoryElements = document.queryAll("div[class^='adtm_column adtm_column_'] > h5 > a");
        List<Element> products = new ArrayList<Element>();
        List<String> categoryLinks = new ArrayList<String>();
        Element element;
        String link;
        
        for (int i =0 ; i < categoryElements.size() ; i++)
        {
        	element = categoryElements.get(i);
        	link = element.getAttribute("href").get();
        	categoryLinks.add(link);
        	System.out.println(link);
        }
        
        Document subDocument;
        Page subPage;
        Element product;
        String prix , desc , nom , pLink ;
        
        for (int i =0 ; i < categoryLinks.size() ; i++)
        {
        	subPage = webkit.navigate(categoryLinks.get(i));
        	subDocument = subPage.getDocument();
        	
        	//subPage.show();
        	
        	products = subDocument.queryAll("li[class^='ajax_block_product']");
        	
        	for(int j=0; j<products.size();j++)
        	{
	        	product = products.get(j);
	    		prix = product.query("span[class='price']").get().getInnerHTML();
	    		nom = product.query("div[id='produit_liste_texte'] > div > h2 > a").get().getInnerHTML();
	    		desc = product.query("div[id='produit_liste_texte'] > div > p > a").get().getInnerHTML();
	    		pLink = product.query("div[id='produit_liste_texte'] > div > h2 > a").get().getAttribute("href").get();
	    		System.out.println( nom + "\n" + prix  + "\n" + desc + "\n" + pLink + "\n");
	       	}
        }
        page.close();
    	webkit.shutdown();
    }
    
    private static void emptyFile(String fileName)
    {
    	File file = new File (fileName);
    	file.delete();
    }
    
    private static void saveSearchResults(String fileName ,String data)
    {
    	try
    	{	
    		File file = new File (fileName);
        	FileWriter fileWriter = new FileWriter (file,true);
        	PrintWriter printWriter = new PrintWriter(fileWriter);
        	printWriter.println(data);
        	printWriter.flush();
        	printWriter.close();
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
    		
    	}
    	
    		
    }

}