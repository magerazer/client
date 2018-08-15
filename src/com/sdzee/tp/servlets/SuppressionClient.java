package com.sdzee.tp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Client;

public class SuppressionClient extends HttpServlet {

    public static final String  ATT_SESSION_LISTE_CLIENTS = "listeSessionClients";
    public static final String  VUE_LISTE_CLI             = "/WEB-INF/listerClients.jsp";
    private static final String ATT_NOM                   = "nom";

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {

        HttpSession session = req.getSession();

        List<Client> listeClients = (ArrayList<Client>) session.getAttribute( ATT_SESSION_LISTE_CLIENTS );

        String nom = req.getParameter( ATT_NOM );
        Client obj = new Client( nom, "", "", "", "" );

        for ( int i = 0; i < listeClients.size(); i++ ) {
            if ( listeClients.get( i ).equals( obj ) ) {
                listeClients.remove( listeClients.get( i ) );
                i--;
            }
        }

        session.setAttribute( ATT_SESSION_LISTE_CLIENTS, listeClients );

        this.getServletContext().getRequestDispatcher( VUE_LISTE_CLI ).forward( req, res );

    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

    }

}
