package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.ConnexionBD;

public class ProductService {
	
	private static final int IGNORED_CATEGORY = 99;
	ConnexionBD conn = new ConnexionBD();
	
	public int getProductByKey(String key)
	{
		String[] keys;
		keys = key.split(" ");
		System.out.println(keys.length);
		
		String requestBase= "select id from products where name like '%";
		String requestEnd = "%'";
		String intersect = " intersect ";
		String fullRequest="";
		
		
		fullRequest = requestBase + keys[0] + requestEnd ;
		System.out.println(fullRequest);
		
		for ( int i = 1 ; i < keys.length ; i++ )
		{
			fullRequest += intersect;
			fullRequest += " " + requestBase + keys[i] + requestEnd ;
		}
		
		System.out.println(fullRequest);
		
		ResultSet res = conn.ReadRequest(fullRequest);
		
		int id = -1;
		
		try {
			if (res.next()) id = res.getInt("id");
			else return 0;
			if (res.next()) return -1;
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
		
		
	}

	public ResultSet getAllProducts()
	{
		return conn.ReadRequest("select * from allproducts where categoryid != "+ IGNORED_CATEGORY 
				+ " order by categoryid");
	}
	
	public void resetCategories()
	{
		conn.Update("update allproducts set categoryid=0");
	}
	
	public void stop()
	{
		conn.arret();
	}
	
	public static void main(String[] args)
	{	
		ProductService m= new ProductService();
		m.getProductByKey("asus pc x550uv");
		
	}
}
