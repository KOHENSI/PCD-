package com.servlets.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.beans.Product;
import com.model.dao.CategoryDao;
import com.model.dao.DAOFactory;
import com.model.dao.ProductDao;

/**
 * Servlet ValiderServlet
 *
 */
//@WebServlet("/compareb")
 public class CompareButton extends javax.servlet.http.HttpServlet 
                          implements javax.servlet.Servlet {
  
  /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CompareButton() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, 
	 *                                           HttpServletResponse response)
	 */
	private ProductDao productDao;
	private CategoryDao categoryDao;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.productDao = daoFactory.getProductDao();
        this.categoryDao = daoFactory.getCategoryDao();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	                     throws ServletException, IOException {
    
    int id = Integer.parseInt(request.getParameter("id"));

    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");

    String resultat = Integer.toString(id);
    
    HttpSession session = request.getSession();
    
    List<Integer> lp = (List<Integer>) session.getAttribute("pToCompare");
    
    if(lp.contains(id))
    {
    	lp.remove(lp.indexOf(id));
    }
    else 
    {
    	lp.add(id);
    }
    
    System.out.println(lp);
    
    session.setAttribute("pToCompare",lp);
    
    List<Product> listProducts = new ArrayList<Product>();
    String result = "";
    for (int i = 0; i<lp.size();i++)
    {
    	
    	Product p =productDao.getProductById(lp.get(i).toString());
    	listProducts.add(p);
    	String name = p.getName();
    	String imglink =p.getImglink();
    	System.out.println(lp.get(i) + " " + name + " " + imglink);
    	result+= "<product><name>"+ name +"</name><image>"+imglink+"</image><id>"+lp.get(i)+"</id></product>";
    }
    
    session.setAttribute("productstocompare",listProducts);
    
    response.getWriter().write("<message>"+result+"</message>");
    
   
  }
}