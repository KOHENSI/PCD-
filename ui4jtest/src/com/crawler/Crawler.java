package com.crawler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.database.ConnexionBD;
import com.entity.Product;
import com.service.BrandService;
import com.service.CategoryService;
import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.BrowserFactory;
import com.ui4j.api.browser.BrowserType;
import com.ui4j.api.browser.Page;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;

public abstract class Crawler {
	
	protected String venderUrl;
	protected String venderName;
	protected int venderID;
	protected static final String VENDERS_TABLE = "venders";
	protected static final String PRODUCTS_TABLE = "allproducts";
	protected static final String BRANDS_TABLE = "brands";
	
	//Lists
	List<String> categoryLinks = new ArrayList<String>();
	List<String> productLinks = new ArrayList<String>();
	List<Element> categoryElements = new ArrayList<Element>();
	List<Element> subCategoryElements = new ArrayList<Element>();
	List<Element> products = new ArrayList<Element>();
	List<Integer> categoriesToIgnore = new ArrayList<Integer>();
	
	//Eelments
	Element product;
	Element element;
	
	//Pgaes
	Page page;
	Page subPage;
	
	//documents
	Document document;
	Document subDocument;
	
	//Selectors
	
	protected String mainCategorySelector;
	protected String subCategorySelector;
	protected String categorySelector;
	protected String categoryNameSelector;
	protected String productBlockSelector;
	protected String nameSelector;
	protected String priceSelector ;
	protected String descSelector ;
	protected String linkSelector;
	protected String brandSelector;
	protected String referenceSelector;
	protected String imageLinkSelector;
	protected String paginationSelector;
	protected String loadingContent;
	
	protected CategoryService categoryService = new CategoryService();
	protected BrandService brandService = new BrandService();

	public String getUrl() {
		return venderUrl;
	}

	public void setUrl(String url) {
		this.venderUrl = url;
	}

	public String getName() {
		return venderName;
	}

	public void setName(String name) {
		this.venderName = name;
	}
	
	
	protected static BrowserEngine webkit = BrowserFactory.getBrowser(BrowserType.WebKit);
	//DataBaseConnexion
	protected static ConnexionBD conn = new ConnexionBD();
	
	public void crawl(){
		getCategoryLinks();
		getProductLinks();
		removeOldProducts();
		getProducts();
	}; 
	
protected void getCategoryLinks() {
		
		Page page = webkit
                .navigate(venderUrl);
        Document document = page.getDocument();

        categoryElements = document.queryAll(mainCategorySelector);
      
        Element element;
        String link;
        
        for (int i =0 ; i < categoryElements.size() ; i++)
        {       	
        	if(categoriesToIgnore.contains(i)) continue;
        	
        	element = categoryElements.get(i);
        	subCategoryElements = element.queryAll(subCategorySelector);
        	Optional<String> os;
        	for(int j =0 ; j < subCategoryElements.size() ; j++)
        	{
        		os = subCategoryElements.get(j).getAttribute("href");
        		if (os.isPresent())
        		{
        			link=os.get();
        			if (!link.contains(venderUrl)) link=venderUrl+link;
        			categoryLinks.add(link);
        			System.out.println(link);

        		}else
        			continue;
        	}
        	
        }
    }
	
	protected void getProductLinks()
	{
		
	}

	protected abstract void getProducts();
	
	protected boolean saveProduct(Product p){
	
		String request ="insert into " + PRODUCTS_TABLE 
				+"(categoryid,category,brandid,name,ref,description,price,venderid,link,imagelink)" + " values"
				+"("+p.getCategoryID()+",'"
					+p.getCategory()+"',"
					+brandService.getBrandID(p.getBrand())+ ",'"
					+p.getName()+"','"
					+p.getRef()+"','"
					+p.getDesc()+"',"
					+p.getPrice()+","
					+this.venderID+",'"
					+p.getLink()+"','"
					+p.getImgaeLink()
					+ "')";
		System.out.println(request);
		boolean res = conn.Update(request);
	
		if (res) return true;
		else return false;
	}
	
	protected boolean removeOldProducts(){
		
		String request ="delete from " + PRODUCTS_TABLE + " where  venderid = " + venderID;
		System.out.println(request);
		boolean res = conn.Update(request);
	
		if (res) return true;
		else return false;
	}

	protected int getVenderID(){
		
		ResultSet res = conn.ReadRequest(
				"select id from " + VENDERS_TABLE
				+ " where name = '" + venderName +"'") ;
		
		try {
			res.first();
			System.out.println("the vender " +venderName+ " have the id of "+ res.getInt("id"));
			//conn.arret();
			return (int)res.getInt("id");
		} catch (SQLException e) {
			System.err.println("No ID found for Vender " + venderName );
		}
		return -1;
		
	}
	
	protected int getBrandID(String brandName){
		
		ResultSet res = conn.ReadRequest(
				"select id from " + BRANDS_TABLE
				+ " where name = '" + brandName +"'") ;
		
		try {
			if (res.next())
			{
				System.out.println("the brand  have the id of "+ res.getInt("id"));
				return (int)res.getInt("id");
			}else
			{
				//conn.Update("insert into " +  BRANDS_TABLE +" (name) values " + " ('"+ brandName+"')");
				//return getBrandID(brandName) 0;
				return 0;
			}
		} catch (SQLException e) {
			System.err.println("No ID found for Vender " + venderName );
		}
		return -1;
	}
	
	protected int getCategorieID(String Source){
		return 0;
	}
	
	public void Stop(){
		categoryService.stop();
		brandService.stop();
		//subPage.close();
		conn.arret();
		//page.close();
    	webkit.shutdown();
	}
	
	protected static void waitNSecond(int N) {
	        try {
	        	
	            Thread.sleep(N*1000L);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	 }
}
