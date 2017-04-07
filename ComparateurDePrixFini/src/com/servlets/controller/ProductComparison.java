package com.servlets.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.beans.Product;
import com.model.dao.DAOFactory;
import com.model.dao.ProductDao;

public class ProductComparison extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String   VUE_COMPARE ="/WEB-INF/product_comparison.jsp";
	private static final long serialVersionUID = 1L;
    private ProductDao productDao  ;  
  
    
    
    public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.productDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProductDao();
	}
    


public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	this.getServletContext().getRequestDispatcher(VUE_COMPARE).forward(request, response);
	    
	}


public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	this.getServletContext().getRequestDispatcher(VUE_COMPARE).forward(request, response);
	}

}
