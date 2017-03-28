package com.servlets.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.beans.Member;
import com.model.dao.DAOFactory;
import com.model.dao.MemberDao;
import com.forms.InscriptionForm;

public class Inscription extends HttpServlet {
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String VUE_INSC         = "/WEB-INF/inscription.jsp";

    private MemberDao          memberDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.memberDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getMemberDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
        this.getServletContext().getRequestDispatcher( VUE_INSC ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        InscriptionForm form = new InscriptionForm( memberDao );

        /* Traitement de la requête et récupération du bean en résultant */
        Member member = form.inscrireMember( request );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, member );

        this.getServletContext().getRequestDispatcher( VUE_INSC ).forward( request, response );
    }
}