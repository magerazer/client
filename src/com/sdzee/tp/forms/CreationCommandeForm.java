package com.sdzee.tp.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.beans.Commande;
import com.sdzee.tp.beans.Fichier;

public class CreationCommandeForm {

    public static final String  CHAMP_NOM              = "nomClient";
    public static final String  CHAMP_PRENOM           = "prenomClient";
    public static final String  CHAMP_ADR              = "adresseClient";
    public static final String  CHAMP_TEL              = "telephoneClient";
    public static final String  CHAMP_MAIL             = "emailClient";

    public static final String  CHAMP_DATE             = "dateCommande";
    public static final String  CHAMP_MONTANT          = "montantCommande";

    public static final String  CHAMP_MODE_PAIEMENT    = "modePaiementCommande";
    public static final String  CHAMP_STATUT_PAIEMENT  = "statutPaiementCommande";
    public static final String  CHAMP_MODE_LIVRAISON   = "modeLivraisonCommande";
    public static final String  CHAMP_STATUT_LIVRAISON = "statutLivraisonCommande";

    public static final String  FORMAT_DATE            = "dd/MM/yyyy à HH:mm:ss";

    public static final String  ATT_ERREURS            = "erreurs";
    public static final String  ATT_RESULTAT           = "resultat";

    private String              resultat;
    private Map<String, String> erreurs                = new HashMap<String, String>();

    public Commande creationCommande( HttpServletRequest req, Fichier fichier ) {
        /*
         * String nom = req.getParameter( CHAMP_NOM ); String prenom =
         * req.getParameter( CHAMP_PRENOM ); String adresse = req.getParameter(
         * CHAMP_ADR ); String telephone = req.getParameter( CHAMP_TEL ); String
         * email = req.getParameter( CHAMP_MAIL );
         */

        CreationClientForm formcli = new CreationClientForm();
        Client client = formcli.inscriptionClient( req, fichier );

        String montant = req.getParameter( CHAMP_MONTANT );

        String modePaiement = req.getParameter( CHAMP_MODE_PAIEMENT );
        String statutPaiement = req.getParameter( CHAMP_STATUT_PAIEMENT );
        String modeLivraison = req.getParameter( CHAMP_MODE_LIVRAISON );
        String statutLivraison = req.getParameter( CHAMP_STATUT_LIVRAISON );

        erreurs = formcli.getErreurs();

        DateTime dt = new DateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern( FORMAT_DATE );

        String date = fmt.print( dt );

        double montantD = 0.0;
        try {
            montantD = validationMontant( montant );
        } catch ( Exception e ) {
            erreurs.put( "montant", e.getMessage() );
        }

        try {
            validationModePaiement( modePaiement );
        } catch ( Exception e ) {
            erreurs.put( "modePaiement", e.getMessage() );
        }

        try {
            validationStatutPaiement( statutPaiement );
        } catch ( Exception e ) {
            erreurs.put( "statutPaiement", e.getMessage() );
        }

        try {
            validationModePaiement( modeLivraison );
        } catch ( Exception e ) {
            erreurs.put( "modeLivraison", e.getMessage() );
        }

        try {
            validationStatutLivraison( statutLivraison );
        } catch ( Exception e ) {
            erreurs.put( "statutLivraison", e.getMessage() );
        }

        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la création de la commande.";
        } else {
            resultat = "Echec de la création de la commande.";
        }

        Commande commande = new Commande( client, date, montantD, modePaiement, statutPaiement, modeLivraison,
                statutLivraison );

        return commande;
    }

    private void validationNom( String nom ) throws Exception {
        if ( nom.isEmpty() ) {
            throw new Exception( "Veuillez renseigner le nom" );
        } else {
            if ( nom.length() < 2 ) {
                throw new Exception( "Le nom doit contenir au moins 2 caractères." );
            }
        }
    }

    private void validationPrenom( String prenom ) throws Exception {
        if ( !prenom.isEmpty() && prenom.length() < 2 ) {
            throw new Exception( "Le prenom doit contenir au moins 2 caractères." );
        }

    }

    private void validationTel( String telephone ) throws Exception {
        if ( telephone.isEmpty() ) {
            throw new Exception( "Veuillez renseigner le numéro de téléphone." );
        } else {
            if ( telephone.length() < 4 ) {
                throw new Exception( "Le numéro doit contenir au moins 4 chiffres." );
            } else {
                if ( !telephone.matches( "[0-9]+" ) ) {
                    throw new Exception( "Le numéro doit contenir que des chiffres." );
                }
            }
        }
    }

    private void validationAdresse( String adresse ) throws Exception {
        if ( adresse.isEmpty() ) {
            throw new Exception( "Veuillez renseigner l'adresse" );
        } else {
            if ( adresse.length() < 10 ) {
                throw new Exception( "L'adresse doit contenir au moins 10 caractères." );
            }
        }
    }

    private void validationEmail( String email ) throws Exception {
        if ( !email.isEmpty() && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }

    private double validationMontant( String montant ) throws Exception {
        double montantDouble = 0;
        if ( montant.isEmpty() ) {
            throw new Exception( "Veuillez renseigner le montant." );
        } else {
            try {
                montantDouble = Double.parseDouble( montant );
            } catch ( NumberFormatException e ) {
                throw new Exception( "Le montant doit être un entier décimal." );
            }
            if ( montantDouble < 0 ) {
                throw new Exception( "Le montant doit être positif." );
            }
        }
        return montantDouble;
    }

    private void validationModeLivraison( String modeLivraison ) throws Exception {
        if ( modeLivraison.isEmpty() ) {
            throw new Exception( "Veuillez renseigner le mode de livraison." );
        } else {
            if ( modeLivraison.length() < 2 ) {
                throw new Exception( "Le mode de livraison doit contenir au moins 2 caractères." );
            }
        }
    }

    private void validationStatutLivraison( String statutLivraison ) throws Exception {
        if ( !statutLivraison.isEmpty() && statutLivraison.length() < 2 ) {
            throw new Exception( "Le statut de livraison doit contenir au moins 2 caractères." );
        }

    }

    private void validationModePaiement( String modePaiement ) throws Exception {
        if ( modePaiement.isEmpty() ) {
            throw new Exception( "Veuillez renseigner le mode de paiement." );
        } else {
            if ( modePaiement.length() < 2 ) {
                throw new Exception( "Le mode de paiement doit contenir au moins 2 caractères." );
            }
        }
    }

    private void validationStatutPaiement( String statutPaiement ) throws Exception {
        if ( !statutPaiement.isEmpty() && statutPaiement.length() < 2 ) {
            throw new Exception( "Le statut de paiement doit contenir au moins 2 caractères." );
        }

    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

}
