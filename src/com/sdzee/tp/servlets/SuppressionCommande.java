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
import com.sdzee.tp.beans.Commande;

public class SuppressionCommande extends HttpServlet {

    public static final String  ATT_SESSION_LISTE_COMMANDES = "listeSessionCommandes";
    public static final String  VUE_LISTE_COMM              = "/WEB-INF/listerCommandes.jsp";
    private static final String ATT_DATE                    = "date";

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {

        HttpSession session = req.getSession();

        List<Commande> listeCommandes = (ArrayList<Commande>) session.getAttribute( ATT_SESSION_LISTE_COMMANDES );

        List<Client> listeClients = (ArrayList<Client>) session.getAttribute( ATT_SESSION_LISTE_COMMANDES );

        String date = req.getParameter( ATT_DATE );
        Commande obj = new Commande( null, date, 0, "", "", "", "" );

        if ( listeCommandes == null )
            listeCommandes = new ArrayList<Commande>();

        for ( int i = 0; i < listeCommandes.size(); i++ ) {
            if ( listeCommandes.get( i ).equals( obj ) ) {
                listeCommandes.remove( listeCommandes.get( i ) );
                i--;
            }
        }

        session.setAttribute( ATT_SESSION_LISTE_COMMANDES, listeCommandes );

        this.getServletContext().getRequestDispatcher( VUE_LISTE_COMM ).forward( req, res );

    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

    }

}
