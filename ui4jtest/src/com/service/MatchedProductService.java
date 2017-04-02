package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.ConnexionBD;

public class MatchedProductService {
	
	private static final String MATCHED_PRODUCTS_TABLE = "products";
	private static final String JOINT_TABLE = "joint";
	
	ConnexionBD conn = new ConnexionBD();
	
	public void deleteMatchedProducts()
	{
		conn.Update("delete from " + MATCHED_PRODUCTS_TABLE);
		conn.Update("delete from " + JOINT_TABLE);
	}

	public ResultSet getAllMatchedProducts()
	{
		return conn.ReadRequest("select * from "+MATCHED_PRODUCTS_TABLE+" as a , "+JOINT_TABLE+" as b where "
				+ "	a.id = b.productid");
	}
	
	public ResultSet getAllMatchedProducts(int categoryid)
	{
		return conn.ReadRequest("select * from "+MATCHED_PRODUCTS_TABLE+" as a , "+JOINT_TABLE+" as b where "
				+ "	a.id = b.productid and a.categoryid = "+categoryid);
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
