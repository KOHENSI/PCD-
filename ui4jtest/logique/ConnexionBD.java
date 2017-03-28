package logique;

import java.sql.*;

//import javax.naming.CompoundName;

public class ConnexionBD {
	
	private Connection connexion;
	private Statement inst;
	private ResultSet resultat;
	
	public ConnexionBD(){
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ii2ilsi","root","scott");
			
			
		} catch (ClassNotFoundException e) {
			
			System.err.println("Probléme de pilote.");
			
		} catch (SQLException e) {
			
			System.err.println("Base de données non trouvée.");
		
		}
		try {
			inst = connexion.createStatement();
		} catch (SQLException e) {
			
			System.err.println("Instruction non crée.");
		}
	}
	
	public boolean miseAJour(String sql){
		try {
			inst.executeUpdate(sql);
		} catch (SQLException e) {

			System.err.println(".");
			return false;
		}
		return true;
		
	}
	
	public void lire(String requete){
		
		try {
			resultat=inst.executeQuery(requete);
			
			System.out.println(resultat);
			int i =0;
			while (resultat.next())
			{
				i++;
				int num = resultat.getInt("id");
				String ch = resultat.getString("nom");
				System.out.println("ligne " + i + " id " + num + " nom " + ch );
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(".");
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
		
		conn.lire("select * from etudiant");
		
		conn.miseAJour("delete from etudiant where id in ('5','7')");
		
		conn.arret();
		
		System.out.println("( ͡° ͜ʖ ͡°) (๏ᗝ๏ )");
		
	}
}
