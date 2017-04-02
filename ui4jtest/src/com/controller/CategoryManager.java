package com.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.database.ConnexionBD;
import com.service.CategoryService;

public class CategoryManager {
	
	private static final String PRODUCTS_TABLE = "allproducts";
	CategoryService categoryService = new CategoryService();
	private Scanner sc;
	
	ConnexionBD conn = new ConnexionBD();
	ConnexionBD conn2 = new ConnexionBD();
	
	public void resetCategories()
	{
		categoryService.resetCategories();
		categoryService.resetCategoryKeys();
		conn.Update("update "+PRODUCTS_TABLE+" set categoryid=0");
	}
	
	public void affectCategoryToAll(){
		
		
		
		ResultSet res;
		
		res = conn.ReadRequest("select id , category from allproducts where categoryid = 0");
		
		String categoryName;
		int categoryId;
		
		sc = new Scanner(System.in);
		
		try {
			while( res.next() )
			{
				categoryName = res.getString("category");
				categoryId = categoryService.lookForCategoryV2(categoryName);
				
				if ( categoryId == 0 )
				{
					System.out.println("Enter the category id for the keyword " + categoryName);
					
					int readCategoryid = -1;
					while(readCategoryid < 0)
						readCategoryid = sc.nextInt();
					
					if ( readCategoryid == 0 ) continue;
					
					String request = "insert into categorykeys ( categoryid , keyword ) values "
							+ "("+readCategoryid+","
							+ "'"+categoryName+"')" ;
					System.out.println(request);
					conn2.Update(request);
					
					categoryId = categoryService.lookForCategoryV2(categoryName);
					
				}
				
				conn2.Update("update allproducts "
						+ " set categoryid = " + categoryId
						+ " where id = "+ res.getInt("id") );
				
				System.out.println( categoryName+" corresponds to id " + categoryId);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop(){
		conn2.arret();
		conn.arret();
	}
	
	public static void main(String[] args){
		
		CategoryManager manager = new CategoryManager();
		
		//manager.resetCategories();
		manager.affectCategoryToAll();
		manager.stop();		
	}
	
}
