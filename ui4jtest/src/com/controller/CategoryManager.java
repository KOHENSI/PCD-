package com.controller;

import java.sql.ResultSet;

import com.database.ConnexionBD;

public class CategoryManager {
	public void affectCategoryToAll(){
		ConnexionBD conn = new ConnexionBD();
		
		ResultSet res;
		
		res = conn.ReadRequest("select category from allproducts where category id = 0");
		
		while()
		
		
		conn.arret();
	}
}
