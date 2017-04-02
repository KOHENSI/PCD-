package com.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.ConnexionBD;
import com.entity.Product;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public class CategoryService {
	
	protected static final int UNRELATED_CATEGORY_ID = 99;
	
	protected static final String CATEGORIES_TABLE = "categories";
	protected static final String CATEGORYKEYS_TABLE = "categorykeys";
	
	ConnexionBD conn = new ConnexionBD ();
	 
	public String removeUselessKeys(String source , int categoryid){
		
		//remove useless keys++
		ResultSet res = conn.ReadRequest("select keyword from categorykeys where "
				+ "categoryid =" + categoryid);
		
		try {
			while(res.next()){
				source = source.replaceAll(res.getString("keyword"),"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//remove useless keys--
		return source;
	}
	
	public int lookForCategoryV2(String source){
		
		NormalizedLevenshtein l = new NormalizedLevenshtein();
		ResultSet res;
		
		res = conn.ReadRequest("select * from categorykeys");
		
		try {
			while(res.next())
			{
				if (l.distance(source.toLowerCase().trim(), res.getString("keyword").toLowerCase().trim()) == 0 )
					{
						//System.out.println(res.getString("keyword") + " " +  source +" " +l.distance(source, res.getString("keyword")));
						return res.getInt("categoryid");
					}
			}
			return 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int lookForCategory(String source){
		
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
		
		return 0;
	}
	
	public void removeKeysFromName (Product p){
		
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
	}
	public void addCategoty(int pid ,String name ){
		
		conn.Update("insert into "+ CATEGORIES_TABLE + " (pid , name )"
				+ "values"
				+ "(" +pid +" , '"+name+"')");
	}
	
	public void addCategoryKey(int catid , String name ){
		conn.Update("insert into "+ CATEGORYKEYS_TABLE + " ( categoryid , keyword )"
				+ "values"
				+ "(" + catid +" , '"+name+"')");
	}
	
	public void resetCategories(){
		conn.Update("delete from "+ CATEGORIES_TABLE );
		conn.Update("alter table "+ CATEGORIES_TABLE +" auto_increment = 1");
		try {
			readCategories();
		} catch (IOException e) {
			System.err.println("Could not read categories from file");
		}

	}
	
	public void readCategories() throws IOException {
		System.out.println("Reading from file");
		// Open the file
		File fileDir = new File("categories.sql");

        BufferedReader br = new BufferedReader(
           new InputStreamReader(new FileInputStream(fileDir), "UTF-8"));
			String strLine;

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  System.out.println (strLine);
			  conn.Update(strLine);
			}

			//Close the input stream
			br.close();
			
	}

	public void resetCategoryKeys(){
			conn.Update("delete from "+ CATEGORYKEYS_TABLE );
			conn.Update("alter table "+ CATEGORYKEYS_TABLE +" auto_increment = 1");
	}
	
	public int getCategoryID(String name)
	{
		
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
		
		return 0;
	}
	public void stop(){
		conn.arret();
	}

}
