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
import com.sdzee.tp.beans.Fichier;
import com.sdzee.tp.forms.CreationClientForm;
import com.sdzee.tp.forms.UploadForm;

public class CreationClient extends HttpServlet {

    public static final String CHEMIN                    = "chemin";

    public static final String ATT_FICHIER               = "fichier";
    public static final String ATT_FORM                  = "form";

    public static final String ATT_MSG                   = "message";
    public static final String ATT_CLI                   = "client";
    public static final String ATT_ERR                   = "erreur";

    public static final String ATT_FORMCLI               = "form";

    public static final String VUE_AFFICHAGE             = "/WEB-INF/afficherClient.jsp";
    public static final String VUE_CREATION              = "/WEB-INF/creerClient.jsp";

    public static final String ATT_SESSION_CLIENT        = "sessionClient";
    public static final String ATT_SESSION_LISTE_CLIENTS = "listeSessionClients";

    private List<Client>       listeClients              = new ArrayList<Client>();
    private Client             cli1                      = new Client( "Dudu", "Marcel", "12 rue des Moulins à Vent",
            "0601060106", "marcel.dudu@gmail.com" );;
    private Client             cli2                      = new Client( "Routard", "Cedric", "4 rue des Hortensias",
            "0605060708", "cedric.routard@gmail.com" );;
    private Client             cli3                      = new Client( "Routard", "Cedric", "4 rue des Hortensias",
            "0605060708", "cedric.routard@gmail.com" );;
    private Client             cli4                      = new Client( "Routard", "Cedric", "4 rue des Hortensias",
            "0605060708", "cedric.routard@gmail.com" );;

    public CreationClient() {
        listeClients.add( cli1 );
        listeClients.add( cli2 );
        listeClients.add( cli3 );
        listeClients.add( cli4 );

    }

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

        /* Récupération de la session depuis la requête */
        HttpSession session = req.getSession();

        /*
         * Lecture du paramètre 'chemin' passé à la servlet via la déclaration
         * dans le web.xml
         */
        String chemin = this.getServletConfig().getInitParameter( CHEMIN );

        /* Préparation de l'objet formulaire */
        UploadForm formUpload = new UploadForm();

        /* Traitement de la requête et récupération du bean en résultant */
        Fichier fichier = formUpload.enregistrerFichier( req, chemin );

        CreationClientForm form = new CreationClientForm();

        Client client = form.inscriptionClient( req );

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Client à la session, sinon suppression du bean de la session.
         */
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute( ATT_SESSION_CLIENT, client );
            listeClients.add( client );
            session.setAttribute( ATT_SESSION_LISTE_CLIENTS, listeClients );

        } else {
            session.setAttribute( ATT_SESSION_CLIENT, null );
        }

        // req.setAttribute( ATT_MSG, message );
        req.setAttribute( ATT_CLI, client );
        req.setAttribute( ATT_FORMCLI, form );
        // req.setAttribute( ATT_ERR, erreur );

        /* Stockage du formulaire et du bean dans l'objet request */
        req.setAttribute( ATT_FORM, formUpload );
        req.setAttribute( ATT_FICHIER, fichier );

        if ( form.getErreurs().isEmpty() ) {
            this.getServletContext().getRequestDispatcher( VUE_AFFICHAGE ).forward( req, resp );
        } else {
            this.getServletContext().getRequestDispatcher( VUE_CREATION ).forward( req, resp );
        }

    }

    public Client getCli1() {
        return cli1;
    }

    public Client getCli2() {
        return cli2;
    }

    public Client getCli3() {
        return cli3;
    }

    public Client getCli4() {
        return cli4;
    }

}
