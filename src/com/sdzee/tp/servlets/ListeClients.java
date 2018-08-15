package com.sdzee.tp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Client;

/**
 * Servlet implementation class ListeClients
 */
@WebServlet( "/ListeClients" )
public class ListeClients extends HttpServlet {

    public static final String ATT_SESSION_CLIENT        = "sessionClient";
    public static final String ATT_SESSION_LISTE_CLIENTS = "listeSessionClients";
    public static final String VUE_LISTE_CLI             = "/WEB-INF/listerClients.jsp";

    protected void doGet( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {

        HttpSession session = req.getSession();

        List<Client> listeClients = (ArrayList<Client>) session.getAttribute( ATT_SESSION_LISTE_CLIENTS );

        this.getServletContext().getRequestDispatcher( VUE_LISTE_CLI ).forward( req, res );

    }

    protected void doPost( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {
        System.out.println( "HERRRRRRRRRRRREEEEEEEEEEEEEOOOOO§§§§§§§§" );
        HttpSession session = req.getSession();

        List<Client> listeClients = (ArrayList<Client>) session.getAttribute( ATT_SESSION_LISTE_CLIENTS );

        if ( listeClients == null )
            listeClients = new ArrayList<Client>();

        listeClients.add( new CreationClient().getCli1() );
        listeClients.add( new CreationClient().getCli2() );
        listeClients.add( new CreationClient().getCli3() );
        listeClients.add( new CreationClient().getCli4() );

        for ( int i = 0; i < listeClients.size(); i++ ) {
            System.out.println( listeClients.get( i ) );
        }

        session.setAttribute( ATT_SESSION_LISTE_CLIENTS, listeClients );

        this.getServletContext().getRequestDispatcher( VUE_LISTE_CLI ).forward( req, res );

    }

}
