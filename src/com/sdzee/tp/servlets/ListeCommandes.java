package com.sdzee.tp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Commande;

public class ListeCommandes extends HttpServlet {

    private static final String VUE_LISTE_COMM              = "/WEB-INF/listerCommandes.jsp";
    private static final String ATT_SESSION_LISTE_COMMANDES = "listeSessionCommandes";

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher( VUE_LISTE_COMM ).forward( req, res );
    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {

        HttpSession session = req.getSession();

        List<Commande> listeCommandes = (ArrayList<Commande>) session.getAttribute( ATT_SESSION_LISTE_COMMANDES );

        if ( listeCommandes == null )
            listeCommandes = new ArrayList<Commande>();

        listeCommandes.add(
                new Commande( new CreationClient().getCli1(), "02/05/2018", 12.321, "CB", "", "chronoppost", "" ) );
        listeCommandes.add(
                new Commande( new CreationClient().getCli2(), "12/05/2018", 412.321, "CB", "", "colissimo", "" ) );
        listeCommandes.add(
                new Commande( new CreationClient().getCli3(), "23/03/2018", 921.55, "CB", "", "chronoppost", "" ) );
        listeCommandes.add(
                new Commande( new CreationClient().getCli4(), "12/11/2018", 4.31, "CB", "", "colissimo", "" ) );

        session.setAttribute( ATT_SESSION_LISTE_COMMANDES, listeCommandes );

        this.getServletContext().getRequestDispatcher( VUE_LISTE_COMM ).forward( req, res );

    }

}
