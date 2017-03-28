package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.ConnexionBD;

public class BrandService {
	private static final String BRANDS_TABLE = "brands";

	public void deleteAllBrands(){
		ConnexionBD conn = new ConnexionBD();
		
		conn.Update("delete from "+ BRANDS_TABLE );
		
		conn.arret();
	}
	
	public String deleteBrands(String source){
		ConnexionBD conn = new ConnexionBD();
		
		source = source.toLowerCase().replaceAll("[^a-z]", " ").replaceAll(" +", " ").trim();
		
		//remove useless keys++
		ResultSet res = conn.ReadRequest("select name from "+BRANDS_TABLE);
		
		try {
			while(res.next()){
				String exp = new String (res.getString("name").trim());
				if (source.contains(exp))
				{
					source = source.replaceAll(exp,"");
					System.out.println(source);
					return source;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		conn.arret();
		return source;
		
	}
	public int getBrandID(String brandName){
		
		ConnexionBD conn = new ConnexionBD();
		
		brandName=brandName.toLowerCase().replaceAll("[^a-z]", "");
		
		if (brandName.length()<=2)
		{
			conn.arret();
			return 0;
		}
		
		ResultSet res = conn.ReadRequest(
				"select id from " + BRANDS_TABLE
				+ " where name = '" + brandName +"'") ;
		
		try {
			if (res.next())
			{
				int id =  res.getInt("id");
				System.out.println("the brand  have the id of "+id);
				conn.arret();
				return id;
			}else
			{
				conn.Update("insert into " +  BRANDS_TABLE +" (name) values " + " ('"+ brandName+"')");
				conn.arret();
				return getBrandID(brandName);
			}
		} catch (SQLException e) 
		{
		
		}
		conn.arret();
		return -1;
	}
}
