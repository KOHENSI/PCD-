package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.ConnexionBD;
import com.entity.Product;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public class CategoryService {
	
	protected static final String CATEGORIES_TABLE = "categories";
	protected static final String CATEGORIYKEYS_TABLE = "categorykeys";
	 
	public String removeUselessKeys(String source){
		ConnexionBD conn = new ConnexionBD ();
		
		source.toLowerCase().replaceAll("[^\\p{L}]", " ").replaceAll(" +", " ").trim();
		
		//remove useless keys++
		ResultSet res = conn.ReadRequest("select keyword from categorykeys where "
				+ "categoryid = 0 ");
		
		try {
			while(res.next()){
				source.replaceAll(res.getString("keyword"),"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//remove useless keys--
		conn.arret();
		return source;
	}
	
	public int lookForCategoryV2(String source){
		
		ConnexionBD conn = new ConnexionBD ();
		NormalizedLevenshtein l = new NormalizedLevenshtein();
		ResultSet res;
		
		removeUselessKeys(source);
		
		res = conn.ReadRequest("select * from categorykeys");
		
		
		try {
			while(res.next())
			{
				if (l.distance(source.toLowerCase().trim(), res.getString("keyword").toLowerCase().trim()) < 0.1 )
					{
						System.out.println(res.getString("keyword") + " " +  source +" " +l.distance(source, res.getString("keyword")));
						return res.getInt("categoryid");
					}
			}
			return 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		conn.arret();
		return 0;
	}
	
	public int lookForCategory(String source){
		
		ConnexionBD conn = new ConnexionBD ();
	
		ResultSet res = conn.ReadRequest("select keyword from categorykeys where "
				+ "categoryid = 0 ");
		
		try {
			while(res.next()){
				source.replaceAll(res.getString("keyword"),"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		res = conn.ReadRequest("select categoryid from categorykeys where "
				+ "keyword = '" + source + "'");
	
		try {
			if (res.next()) 
				return res.getInt("categoryid");
			else return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		conn.arret();
		return 0;
	}
	
	public void removeKeysFromName (Product p){
		
		ConnexionBD conn = new ConnexionBD ();
		
		ResultSet res = conn.ReadRequest("select keyword from categorykeys where "
				+ "categoryid = 0 or categoryid = "+ p.getCategoryID());
		
		String pName = p.getName();
		
		try {
			while(res.next()){
				pName.replaceAll(res.getString("keyword"),"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		conn.arret();
	}
	public void addCategoty(int pid ,String name ){
		
		ConnexionBD conn = new ConnexionBD ();
		
		conn.Update("insert into "+ CATEGORIES_TABLE + " (pid , name )"
				+ "values"
				+ "(" +pid +" , '"+name+"')");
	  conn.arret();
	}
	
	public void addCategoryKey(int catid , String name ){
		
		ConnexionBD conn = new ConnexionBD ();
		
		conn.Update("insert into "+ CATEGORIYKEYS_TABLE + " ( categoryid , keyword )"
				+ "values"
				+ "(" + catid +" , '"+name+"')");
	  conn.arret();
	}
	
	public void deleteAllCategories(){
	ConnexionBD conn = new ConnexionBD ();
		
		conn.Update("delete from "+ CATEGORIES_TABLE );
		
		conn.arret();
	}
	
	public void deleteAllCategoryKeys(){
		ConnexionBD conn = new ConnexionBD ();
			
			conn.Update("delete from "+ CATEGORIYKEYS_TABLE );
			
			conn.arret();
	}
	
	public int getCategoryID(String name)
	{
		ConnexionBD conn = new ConnexionBD ();
		
		ResultSet res = conn.ReadRequest("select id from "+ CATEGORIES_TABLE +" where name ='" + name+"'");
		
		try {
			if (res.next()) 
				return res.getInt("id");
			else 
				return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		conn.arret();
		return 0;
	}

}
