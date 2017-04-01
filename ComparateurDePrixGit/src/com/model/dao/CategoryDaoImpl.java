package com.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.beans.Category;
import com.model.beans.Product;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class CategoryDaoImpl implements CategoryDao {
	private DAOFactory daoFactory;

    CategoryDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
	@Override
	public List<Category> lister() {
		List<Category> categories = new ArrayList<Category>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = (Connection) daoFactory.getConnection();
            statement = (Statement) connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM categories;");

            while (resultat.next()) {
            	int id = resultat.getInt("id");
            	int pid = resultat.getInt("pid");
            	String name = resultat.getString("name");
            	String icon = resultat.getString("icon");
                
            	Category category = new Category();
                
            	category.setId(id);
                category.setPid(pid);
                category.setName(name);
                category.setIcon(icon);
                
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
	}

}
