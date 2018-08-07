package com.sdzee.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.forms.CreationClientForm;

public class CreationClient extends HttpServlet {

    public static final String ATT_MSG       = "message";
    public static final String ATT_CLI       = "client";
    public static final String ATT_ERR       = "erreur";

    public static final String ATT_FORMCLI   = "form";

    public static final String VUE_AFFICHAGE = "/WEB-INF/afficherClient.jsp";
    public static final String VUE_CREATION  = "/WEB-INF/creerClient.jsp";

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher( VUE_CREATION ).forward( req, resp );

    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

        /*
         * String message = ""; boolean erreur = false; if ( nom.equals( "" ) ||
         * adresse.equals( "" ) || telephone.equals( "" ) ) { message =
         * "Erreur - Vous n'avez pas rempli tous les champs obligatoires. " +
         * "<br> <a href=\"/tp1/creerClient.jsp\">Cliquez ici</a>" +
         * " pour accéder au formulaire de création d'un client."; erreur =
         * true; } else { message = "Client crée avec succès !"; }
         */

        CreationClientForm form = new CreationClientForm();

        Client client = form.inscriptionClient( req );

        // req.setAttribute( ATT_MSG, message );
        req.setAttribute( ATT_CLI, client );
        req.setAttribute( ATT_FORMCLI, form );
        // req.setAttribute( ATT_ERR, erreur );

        if ( form.getErreurs().isEmpty() ) {
            this.getServletContext().getRequestDispatcher( VUE_AFFICHAGE ).forward( req, resp );
        } else {
            this.getServletContext().getRequestDispatcher( VUE_CREATION ).forward( req, resp );
        }

    }

}
