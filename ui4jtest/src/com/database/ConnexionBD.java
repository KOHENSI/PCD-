package com.database;

import java.sql.*;

//import javax.naming.CompoundName;

public class ConnexionBD {
	
	private Connection connexion;
	private Statement inst;
	private ResultSet resultat;
	
	public ConnexionBD(){
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcd","root","scott");
			
			
		} catch (ClassNotFoundException e) {
			
			System.err.println("Probléme de pilote.");
			
		} catch (SQLException e) {
			
			System.err.println("Base de données non trouvée.");
		
		}
		try {
			inst = connexion.createStatement();
		} catch (SQLException e) {
			
			System.err.println("Instruction not created");
		}
	}
	
	public boolean Update(String sql){
		try {
			inst.executeUpdate(sql);
		} catch (SQLException e) {

			System.err.println("Failed to update");
			return false;
		}
		return true;
		
	}
	
	public ResultSet ReadRequest(String requete){
		
		try {
			resultat=inst.executeQuery(requete);
			return resultat;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("no results");
			return null;
		}
	}
	
	public void arret(){
		
		try {
			inst.close();
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		
		ConnexionBD conn = new ConnexionBD();
		
		conn.ReadRequest("select * from venders");
		
		//conn.miseAJour("delete from etudiant where id in ('5','7')");
		
		conn.arret();
		
	}
}
