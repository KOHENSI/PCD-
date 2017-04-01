package com.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.beans.Product;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ProductDaoImpl implements ProductDao {
	  private DAOFactory daoFactory;

	    ProductDaoImpl(DAOFactory daoFactory) {
	        this.daoFactory = daoFactory;
	    }
	    @Override
	    public List<Product> lister() {
	        List<Product> products = new ArrayList<Product>();
	        Connection connexion = null;
	        Statement statement = null;
	        ResultSet resultat = null;

	        try {
	            connexion = (Connection) daoFactory.getConnection();
	            statement = (Statement) connexion.createStatement();
	            resultat = statement.executeQuery("SELECT * FROM allproducts;");

	            while (resultat.next()) {
	            	int id = resultat.getInt("id");
	            	int catid = resultat.getInt("categoryid");;
	            	String catergory = resultat.getString("category");
	            	int brandid = resultat.getInt("brandid");
	            	String name = resultat.getString("name");
	            	String description = resultat.getString("description");
	            	int price = resultat.getInt("price");
	            	int vondorid = resultat.getInt("venderid");
	            	String link = resultat.getString("link");
	            	String imglink = resultat.getString("imagelink");
	                
	            	Product product = new Product();
	                
	            	product.setId(id);
	                product.setCatid(catid);
	                product.setCatergory(catergory);
	                product.setBrandid(brandid);
	                product.setName(name);
	                product.setDescription(description);
	                product.setPrice(price);
	                product.setVondorid(vondorid);
	                product.setLink(link);
	                product.setImglink(imglink);

	                products.add(product);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return products;
	    }
	    @Override
	    public List<Product> listerParCategorie(String idp) {
	        List<Product> products = new ArrayList<Product>();
	        Connection connexion = null;
	        Statement statementcat = null;
	        Statement statement = null;
	        ResultSet resultat = null;
	        ResultSet resultatcat = null;

	        try {
	            connexion = (Connection) daoFactory.getConnection();
	            statementcat = (Statement) connexion.createStatement();
	            statement = (Statement) connexion.createStatement();
	            String requetecat = String.format("SELECT * FROM categories WHERE id=%s or pid=%s;", idp,idp);
	            resultatcat = statementcat.executeQuery(requetecat);
	            while (resultatcat.next()) {
	            	int categoryid = resultatcat.getInt("id");
	            	String requete = String.format("SELECT * FROM allproducts WHERE categoryid=%d;", categoryid);
		            resultat = statement.executeQuery(requete);
		            while (resultat.next()) {
		            	int id = resultat.getInt("id");
		            	int catid = resultat.getInt("categoryid");
		            	String catergory = resultat.getString("category");
		            	int brandid = resultat.getInt("brandid");
		            	String name = resultat.getString("name");
		            	String description = resultat.getString("description");
		            	int price = resultat.getInt("price");
		            	int vondorid = resultat.getInt("venderid");
		            	String link = resultat.getString("link");
		            	String imglink = resultat.getString("imagelink");
		                
		            	Product product = new Product();
		                
		            	product.setId(id);
		                product.setCatid(catid);
		                product.setCatergory(catergory);
		                product.setBrandid(brandid);
		                product.setName(name);
		                product.setDescription(description);
		                product.setPrice(price);
		                product.setVondorid(vondorid);
		                product.setLink(link);
		                product.setImglink(imglink);

		                products.add(product);
		            }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	            
	        return products;
	    }
	  public Product getProductById(String idp)
	  {
		  Product product = new Product();
		  Connection connexion = null;
	        Statement statement = null;
	        ResultSet resultat = null;

	        try {
	            connexion = (Connection) daoFactory.getConnection();
	            statement = (Statement) connexion.createStatement();
	            String requete = String.format("SELECT * FROM allproducts WHERE id=%s;", idp);
	            resultat = statement.executeQuery(requete);
	            while (resultat.next()) {
	            	
	            	int id = resultat.getInt("id");
	            	int catid = resultat.getInt("categoryid");;
	            	String catergory = resultat.getString("category");
	            	int brandid = resultat.getInt("brandid");
	            	String name = resultat.getString("name");
	            	String description = resultat.getString("description");
	            	int price = resultat.getInt("price");
	            	int vondorid = resultat.getInt("venderid");
	            	String link = resultat.getString("link");
	            	String imglink = resultat.getString("imagelink");
	                
	            	
	                
	            	product.setId(id);
	                product.setCatid(catid);
	                product.setCatergory(catergory);
	                product.setBrandid(brandid);
	                product.setName(name);
	                product.setDescription(description);
	                product.setPrice(price);
	                product.setVondorid(vondorid);
	                product.setLink(link);
	                product.setImglink(imglink);
	            	}

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	return product;
	  }
	  
	  
	  public List<Product> listerParMotCle(String keyword){
		  List<Product> products = new ArrayList<Product>();
	        Connection connexion = null;
	        Statement statement = null;
	        ResultSet resultat = null;

	        try {
	            connexion = (Connection) daoFactory.getConnection();
	            statement = (Statement) connexion.createStatement();
	            String requete = String.format("SELECT * FROM allproducts WHERE name LIKE '%%%s%%';", keyword);
	            resultat = statement.executeQuery(requete);
	            while (resultat.next()) {
	            	int id = resultat.getInt("id");
	            	int catid = resultat.getInt("categoryid");;
	            	String catergory = resultat.getString("category");
	            	int brandid = resultat.getInt("brandid");
	            	String name = resultat.getString("name");
	            	String description = resultat.getString("description");
	            	int price = resultat.getInt("price");
	            	int vondorid = resultat.getInt("venderid");
	            	String link = resultat.getString("link");
	            	String imglink = resultat.getString("imagelink");
	                
	            	Product product = new Product();
	                
	            	product.setId(id);
	                product.setCatid(catid);
	                product.setCatergory(catergory);
	                product.setBrandid(brandid);
	                product.setName(name);
	                product.setDescription(description);
	                product.setPrice(price);
	                product.setVondorid(vondorid);
	                product.setLink(link);
	                product.setImglink(imglink);

	                products.add(product);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return products;
	  }
}

