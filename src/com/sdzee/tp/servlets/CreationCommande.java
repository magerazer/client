package com.sdzee.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.tp.beans.Commande;
import com.sdzee.tp.forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {

    public static final String  ATT_MSG       = "message";
    public static final String  ATT_CMD       = "commande";
    private static final String ATT_CLI       = "client";
    public static final String  ATT_ERR       = "erreur";

    public static final String  ATT_FORMCOM   = "form";

    public static final String  VUE_AFFICHAGE = "/WEB-INF/afficherCommande.jsp";
    public static final String  VUE_CREATION  = "/WEB-INF/creerCommande.jsp";
    // private static final String ATT_FORMCLI = "form";

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher( VUE_CREATION ).forward( req, resp );

    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

        /*
         * double montantD; try { montantD = Double.parseDouble(
         * req.getParameter( CHAMP_MONTANT ) ); } catch ( NumberFormatException
         * ex ) { //Initialisation à -1 si le montant n'est pas un nombre
         * correct montantD = -1; }
         */

        /*
         * String message = ""; boolean erreur = false; if ( nom.equals( "" ) ||
         * adresse.equals( "" ) || telephone.equals( "" ) || montantD == -1 ||
         * modePaiement.equals( "" ) || modeLivraison.equals( "" ) ) { message =
         * "Erreur - Vous n'avez pas rempli tous les champs obligatoires. " +
         * "<br> <a href=\"/tp1/creerCommande.jsp\">Cliquez ici</a>" +
         * " pour accéder au formulaire de création d'une commande."; erreur =
         * true; } else { message = "Commande créée avec succès !"; }
         */

        CreationCommandeForm form = new CreationCommandeForm();

        Commande commande = form.creationCommande( req );

        // req.setAttribute( ATT_MSG, message );
        req.setAttribute( ATT_CMD, commande );
        req.setAttribute( ATT_CLI, commande.getClient() );
        req.setAttribute( ATT_FORMCOM, form );
        // req.setAttribute( ATT_FORMCLI, formcli );
        // req.setAttribute( ATT_ERR, erreur );
        // req.setAttribute( ATT_ERREURS, erreurs );
        // req.setAttribute( ATT_RESULTAT, resultat );

        if ( form.getErreurs().isEmpty() ) {
            this.getServletContext().getRequestDispatcher( VUE_AFFICHAGE ).forward( req, resp );
        } else {
            this.getServletContext().getRequestDispatcher( VUE_CREATION ).forward( req, resp );
        }
    }

}
