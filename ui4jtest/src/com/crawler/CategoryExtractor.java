package com.crawler;

import java.util.ArrayList;
import java.util.List;

import com.service.BrandService;
import com.ui4j.api.browser.Page;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;

public class CategoryExtractor extends Crawler{
	String mainCategorySelector;
	String subCategorySelector;
	List<Element> mainCategoryElements = new ArrayList<Element>();
	List<Element> subCategoryElements = new ArrayList<Element>();
	
	BrandService brandService = new BrandService();
	
public CategoryExtractor(){
		
		setName("scoop");
		setUrl("http://www.scoop.com.tn/");
		venderID=getVenderID();
		
		mainCategorySelector="li[class='has-sub']";
		subCategorySelector = "div[class^='categorie'] > li > a ";
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
	public void crawl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void getCategoryLinks() {
		// TODO Auto-generated method stub
		categoryService.deleteAllCategories();
		categoryService.deleteAllCategoryKeys();
        
		Page page = webkit
                .navigate(venderUrl);
        Document document = page.getDocument();
        
        mainCategoryElements = document.queryAll(mainCategorySelector);
        
        System.out.println("--------"+mainCategoryElements.size());
        
        String categoryName;
        
        for (int i =0 ; i < mainCategoryElements.size() ; i++)
        {
        	categoryName=mainCategoryElements.get(i).query("a > span").get().getInnerHTML();
        	System.out.println("*******"+categoryName+"*********");
        	categoryService.addCategoty(0, categoryName.toLowerCase().trim());
        	getSubCategories( categoryService.getCategoryID(categoryName),mainCategoryElements.get(i));
        	
        }
        
	}

	private void getSubCategories(int pid , Element categoryElement) {
		// TODO Auto-generated method stub
		
		subCategoryElements = categoryElement.queryAll(subCategorySelector);
		System.out.println("////////////////////////"+subCategoryElements.size());
		 for (int i =0 ; i < subCategoryElements.size() ; i++)
	        {
	        		String link = subCategoryElements.get(i).getAttribute("href").get();
					if (!link.contains(venderUrl)) link=venderUrl+link;
		        	
					subPage = webkit.navigate(link);
		        	
		        	subDocument = subPage.getDocument();
		      
		        	//subPage.show(true);
		        	
		        	
		        	//In case the page does not exist
		        	if (!subDocument.query("div[id='liste_prod']").isPresent()) continue;
		        	
		        	Element titleElement = subDocument.query("div[class='titre_produit'] h1").get();
		        	String SubCategoryName = titleElement.getInnerHTML().toLowerCase().trim();
		        	
		        	SubCategoryName = brandService.deleteBrands(SubCategoryName);
		        	categoryService.addCategoty(pid, SubCategoryName);
		        	categoryService.addCategoryKey(categoryService.getCategoryID(SubCategoryName) , SubCategoryName);
		        	System.out.println(SubCategoryName);
	        }  	
	}

	@Override
	protected void getProducts() {}
	
	protected void getCategories(){}
	
	public static void main(String[] args){
		CategoryExtractor ce= new CategoryExtractor();
		ce.getCategoryLinks();
		ce.Stop();
	}

}
