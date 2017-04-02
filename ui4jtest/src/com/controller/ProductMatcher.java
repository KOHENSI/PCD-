package com.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.database.ConnexionBD;
import com.entity.Product;
import com.google.common.collect.Sets;
import com.service.CategoryService;
import com.service.MatchedProductService;
import com.service.ProductService;

import info.debatty.java.stringsimilarity.Levenshtein;

public class ProductMatcher {
	
	private static final String MATCHED_PRODUCTS_TABLE = "products";

	private static final String JOINT_TABLE  = "joint";

	ConnexionBD conn = new ConnexionBD();

	CategoryService categoryService = new CategoryService();
	ProductService productService = new ProductService();
	MatchedProductService matchedproductService = new MatchedProductService();
	
	private int productsMatchedByRef;
	private int productsMatchedByName;
	
	public void matchAllProducts()
	{
		matchedproductService.deleteMatchedProducts();
		
		ResultSet notMatchedProducts;
		notMatchedProducts = productService.getAllProducts();
		Product productNotYetMatched = new Product();
		
		while ( (productNotYetMatched = loadNextProduct(notMatchedProducts)) != null )
		{
			matchProduct(productNotYetMatched);
		}
		
		System.out.println(productsMatchedByRef+" Poducts matched by reference");
		System.out.println(productsMatchedByName+" Poducts matched by name");
	}
	
	public void matchProduct(Product p)
	{
		ResultSet matchedProducts = matchedproductService.getAllMatchedProducts(p.getCategoryID());
		Product np = new Product();
		boolean matched = false;
		int id = 0;
		
		while ( (np = loadNextProduct(matchedProducts)) != null )
		{
			if (isSameProduct(p,np))
			{
				try {
					id = matchedProducts.getInt("id");
				} catch (SQLException e) {
					continue;
				}
				matched = true;
				System.out.println(p.getName() + " = " + np.getName());
				break;
			}
		}
		if (!matched)
		{
			saveNewProduct(p);
		}else
		{
			saveMatchedProduct(p,id);
		}
	}
	
	public void saveMatchedProduct(Product p,int id)
	{
		addNewJoint(p, id);
	}
	
	public void saveNewProduct(Product p)
	{
		String request ="insert into " + MATCHED_PRODUCTS_TABLE 
				+"(categoryid,brandid,name,ref)" + " values"
				+"("+p.getCategoryID()+","
					+p.getBrandID()+","
					+"'"+p.getName()+"',"
					+"'"+p.getRef()+"')" ;
					
		conn.Update(request);
		
		ResultSet res;
		
		int id = 0;
		try {
			
		res = conn.ReadRequest("select id from "+ MATCHED_PRODUCTS_TABLE 
					+ " where ref ='"+p.getRef()+"'");
		
		if (res.next()) 
				id = res.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		addNewJoint(p,id);

	}
	
	public void addNewJoint(Product p, int id)
	{	
		String request = "insert into " + JOINT_TABLE 
				 +"(productid , venderid  , price , description , link , imagelink ) values "
				 +"("+id+","
				 +p.getVender()+","
				 +p.getPrice()+","
				+"'"+p.getDesc()+"',"
				 +"'"+p.getLink()+"',"
				 +"'"+p.getImgaeLink()+"'"
				 +")";
		//System.out.println(request);
		conn.Update(request);
		
		
	}
	
	public Product loadNextProduct(ResultSet Products)
	{
		try {
			if (!Products.next()) return null;
		
			Product p = new Product();
			
			p.setBrandID(Products.getInt("brandid"));
			p.setCategoryID(Products.getInt("categoryid"));
			p.setPrice(Products.getInt("price"));
			p.setName(Products.getString("name"));
			p.setDesc(Products.getString("description"));
			p.setRef(Products.getString("ref"));
			p.setVender(Products.getInt("venderid"));
			p.setLink(Products.getString("link"));
			p.setImgaeLink(Products.getString("imagelink"));
			
			return p;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
public boolean isSameProduct(Product p1,Product p2) {
		
		String ref1,ref2;
		ref1=p1.getRef();
		ref2=p2.getRef();
		Levenshtein l = new Levenshtein();
		double ChangeTolerance = 2 ;
		
		//System.out.println(ref1 + " " + ref2);
		//System.out.println(l.distance(ref1, ref2));
		if (p1.getVender() == p2.getVender())
			return false;
		
		if (p1.getBrandID() != p2.getBrandID())
			return false;
		
		if (differentPrices(p1, p2))
			return false;
		
		if (p1.getCategoryID()!= p2.getCategoryID()) return false;
		
		
		if (l.distance(ref1, ref2) < ChangeTolerance )
		{
			System.out.println(ref1 + " SAME REF AS " + ref2);
			productsMatchedByRef++;
			
			return true; 
		}else
		{
			
			String[] tokens1;
			String[] tokens2;
			
			tokens1 = categoryService.removeUselessKeys(p1.getName(), p1.getCategoryID())
						.replaceAll(p1.getBrand(), "").replaceAll("/.*", "").split("[ ]+");
			tokens2 = categoryService.removeUselessKeys(p2.getName(), p2.getCategoryID())
					.replaceAll(p2.getBrand(), "").replaceAll("/.*", "").split("[ ]+");
			
			Set<String> tokensSet1 = new HashSet<String>(Arrays.asList(tokens1));
			Set<String> tokensSet2 = new HashSet<String>(Arrays.asList(tokens2));

			float total = Math.min(tokensSet1.size(), tokensSet2.size());

			Set<String> intersection = Sets.intersection(tokensSet1, tokensSet2);

			float similarity = intersection.size();

			//System.out.println(similarity / total + " " + similarity + " " + total);

			if (similarity / total < 0.7)
				return false;
			//System.out.println(ref1 + " is NOT the same as " + ref2);
			System.out.println(ref1 + " SAME NAME AS " + ref2);
			productsMatchedByName++;
			return true;
		}
			
	}

private boolean differentPrices(Product p1, Product p2) {
	
	/*int max = Math.max(p1.getPrice(), p2.getPrice());
	int min = Math.min(p1.getPrice(), p2.getPrice());

	return ((max * 0.9f) < min);*/
	
	int price1=p1.getPrice();
	int price2=p2.getPrice();
	int sub=Math.abs(price1-price2);
	int avr=(price1+price2)/2;
	if (avr == 0) return false;
	else{
		if(sub/avr > 0.1 ) return true;
		else return false;
	}
}

	
	public void stop()
	{
		productService.stop();
		matchedproductService.stop();
		conn.arret();
	}
	
	public static void main(String[] args)
	{
		ProductMatcher p = new ProductMatcher();
		p.matchAllProducts();
		p.stop();
	}
}
