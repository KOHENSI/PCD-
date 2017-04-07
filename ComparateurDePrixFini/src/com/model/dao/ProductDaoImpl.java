package com.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.beans.Product;
import com.model.beans.Productprice;
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
		Statement statement1 = null;
		ResultSet resultat = null;
		ResultSet resultatjoint = null;

		try {
			connexion = (Connection) daoFactory.getConnection();
			statement = (Statement) connexion.createStatement();
			
			resultat = statement.executeQuery("SELECT * FROM products;");

			while (resultat.next()) {
				int id = resultat.getInt("id");
				int brandid = resultat.getInt("brandid");
				int catid = resultat.getInt("categoryid");
				String name = resultat.getString("name");
				String description = resultat.getString("description");
				String imglink = resultat.getString("imagelink");
				
				List<Productprice> pricedetails = new ArrayList<Productprice>();
				statement1 = (Statement) connexion.createStatement();
				String requetejoint = String.format("SELECT * FROM joint,venders where joint.venderid=venders.id and joint.productid=%d;",id);
				resultatjoint = statement1.executeQuery(requetejoint);
				
				while (resultatjoint.next()) {
				Productprice productprice=new Productprice();
				int price = resultatjoint.getInt(4);
				String link = resultatjoint.getString(5);
				String vendorname = resultatjoint.getString(8);
				String vendorimglink = resultatjoint.getString(10);
				String desc = resultatjoint.getString(3);
				String otheimglink = resultatjoint.getString(6);
				productprice.setDescription(desc);
				productprice.setImglink(otheimglink);
				productprice.setPrice(price);
				productprice.setLink(link);
				productprice.setVendorname(vendorname);
				productprice.setVendorimglink(vendorimglink);
				
				pricedetails.add(productprice);
				}
				
				Product product = new Product();

				product.setId(id);
				product.setCatid(catid);
				product.setBrandid(brandid);
				product.setName(name);
				product.setDescription(description);
				product.setImglink(imglink);
				product.setPricedetails(pricedetails);
				product.setBestprice();

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
		Statement statement = null,statement1=null;
		ResultSet resultat = null,resultatjoint=null;
		ResultSet resultatcat = null;

		try {
			connexion = (Connection) daoFactory.getConnection();
			statementcat = (Statement) connexion.createStatement();
			statement = (Statement) connexion.createStatement();
			String requetecat = String.format("SELECT * FROM categories WHERE id=%s or pid=%s;", idp, idp);
			resultatcat = statementcat.executeQuery(requetecat);
			while (resultatcat.next()) {
				int categoryid = resultatcat.getInt("id");
				String requete = String.format("SELECT * FROM products WHERE categoryid=%d;", categoryid);
				resultat = statement.executeQuery(requete);
				while (resultat.next()) {
					int id = resultat.getInt("id");
					int brandid = resultat.getInt("brandid");
					int catid = resultat.getInt("categoryid");
					String name = resultat.getString("name");
					String description = resultat.getString("description");
					String imglink = resultat.getString("imagelink");
					
					List<Productprice> pricedetails = new ArrayList<Productprice>();
					statement1 = (Statement) connexion.createStatement();
					String requetejoint = String.format("SELECT * FROM joint,venders where joint.venderid=venders.id and joint.productid=%d;",id);
					resultatjoint = statement1.executeQuery(requetejoint);
					
					while (resultatjoint.next()) {
					Productprice productprice=new Productprice();
					int price = resultatjoint.getInt(4);
					String link = resultatjoint.getString(5);
					String vendorname = resultatjoint.getString(8);
					String vendorimglink = resultatjoint.getString(10);
					String desc = resultatjoint.getString(3);
					String otheimglink = resultatjoint.getString(6);
					productprice.setDescription(desc);
					productprice.setImglink(otheimglink);
					productprice.setPrice(price);
					productprice.setLink(link);
					productprice.setVendorname(vendorname);
					productprice.setVendorimglink(vendorimglink);
					
					pricedetails.add(productprice);
					}

					Product product = new Product();

					product.setId(id);
					product.setCatid(catid);
					product.setBrandid(brandid);
					product.setName(name);
					product.setDescription(description);
					product.setImglink(imglink);
					product.setPricedetails(pricedetails);
					product.setBestprice();
					
					products.add(product);
				}			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	public Product getProductById(String idp) {
		Product product = new Product();
		Connection connexion = null;
		Statement statement = null,statement1=null;
		ResultSet resultat = null,resultatjoint=null;

		try {
			connexion = (Connection) daoFactory.getConnection();
			statement = (Statement) connexion.createStatement();
			String requete = String.format("SELECT * FROM products WHERE id=%s;", idp);
			resultat = statement.executeQuery(requete);
			while (resultat.next()) {
				int id = resultat.getInt("id");
				int brandid = resultat.getInt("brandid");
				int catid = resultat.getInt("categoryid");
				String name = resultat.getString("name");
				String description = resultat.getString("description");
				String imglink = resultat.getString("imagelink");
				
				List<Productprice> pricedetails = new ArrayList<Productprice>();
				statement1 = (Statement) connexion.createStatement();
				String requetejoint = String.format("SELECT * FROM joint,venders where joint.venderid=venders.id and joint.productid=%d;",id);
				resultatjoint = statement1.executeQuery(requetejoint);
				
				while (resultatjoint.next()) {
				Productprice productprice=new Productprice();
				int price = resultatjoint.getInt(4);
				String link = resultatjoint.getString(5);
				String vendorname = resultatjoint.getString(8);
				String vendorimglink = resultatjoint.getString(10);
				String desc = resultatjoint.getString(3);
				String otheimglink = resultatjoint.getString(6);
				productprice.setDescription(desc);
				productprice.setImglink(otheimglink);
				productprice.setPrice(price);
				productprice.setLink(link);
				productprice.setVendorname(vendorname);
				productprice.setVendorimglink(vendorimglink);
				
				pricedetails.add(productprice);
				}

				product.setId(id);
				product.setCatid(catid);
				product.setBrandid(brandid);
				product.setName(name);
				product.setDescription(description);
				product.setImglink(imglink);
				product.setPricedetails(pricedetails);
				product.setBestprice();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public List<Product> listerParMotCle(String keyword) {
		List<Product> products = new ArrayList<Product>();
		Connection connexion = null;
		Statement statement = null,statement1=null;
		ResultSet resultat = null,resultatjoint=null;

		try {
			connexion = (Connection) daoFactory.getConnection();
			statement = (Statement) connexion.createStatement();
			String requete = String.format("SELECT * FROM products WHERE name LIKE '%%%s%%';", keyword);
			resultat = statement.executeQuery(requete);
			while (resultat.next()) {
				int id = resultat.getInt("id");
				int brandid = resultat.getInt("brandid");
				int catid = resultat.getInt("categoryid");
				String name = resultat.getString("name");
				String description = resultat.getString("description");
				String imglink = resultat.getString("imagelink");
				
				List<Productprice> pricedetails = new ArrayList<Productprice>();
				statement1 = (Statement) connexion.createStatement();
				String requetejoint = String.format("SELECT * FROM joint,venders where joint.venderid=venders.id and joint.productid=%d;",id);
				resultatjoint = statement1.executeQuery(requetejoint);
				
				while (resultatjoint.next()) {
				Productprice productprice=new Productprice();
				int price = resultatjoint.getInt(4);
				String link = resultatjoint.getString(5);
				String vendorname = resultatjoint.getString(8);
				String vendorimglink = resultatjoint.getString(10);
				String desc = resultatjoint.getString(3);
				String otheimglink = resultatjoint.getString(6);
				productprice.setDescription(desc);
				productprice.setImglink(otheimglink);
				productprice.setPrice(price);
				productprice.setLink(link);
				productprice.setVendorname(vendorname);
				productprice.setVendorimglink(vendorimglink);
				
				pricedetails.add(productprice);
				}

				Product product = new Product();

				product.setId(id);
				product.setCatid(catid);
				product.setBrandid(brandid);
				product.setName(name);
				product.setDescription(description);
				product.setImglink(imglink);
				product.setPricedetails(pricedetails);
				product.setBestprice();

				products.add(product);
			}		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
}
