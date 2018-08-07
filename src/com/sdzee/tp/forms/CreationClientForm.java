package com.sdzee.tp.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sdzee.tp.beans.Client;

public class CreationClientForm {

    public static final String  CHAMP_NOM    = "nomClient";
    public static final String  CHAMP_PRENOM = "prenomClient";
    public static final String  CHAMP_ADR    = "adresseClient";
    public static final String  CHAMP_TEL    = "telephoneClient";
    public static final String  CHAMP_MAIL   = "emailClient";

    private static final String ATT_ERREURS  = "erreurs";
    private static final String ATT_RESULTAT = "resultatClient";

    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public Client inscriptionClient( HttpServletRequest req ) {

        String nom = req.getParameter( CHAMP_NOM );
        String prenom = req.getParameter( CHAMP_PRENOM );
        String adresse = req.getParameter( CHAMP_ADR );
        String telephone = req.getParameter( CHAMP_TEL );
        String email = req.getParameter( CHAMP_MAIL );

        try {
            validationNom( nom );
        } catch ( Exception e ) {
            erreurs.put( "nom", e.getMessage() );
        }

        try {
            validationPrenom( prenom );
        } catch ( Exception e ) {
            erreurs.put( "prenom", e.getMessage() );
        }

        try {
            validationAdresse( adresse );
        } catch ( Exception e ) {
            erreurs.put( "adresse", e.getMessage() );
        }

        try {
            validationTel( telephone );
        } catch ( Exception e ) {
            erreurs.put( "telephone", e.getMessage() );
        }

        try {
            validationEmail( email );
        } catch ( Exception e ) {
            erreurs.put( "email", e.getMessage() );
        }

        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la création du client.";
        } else {
            resultat = "Echec de la création du client.";
        }

        Client client = new Client( nom, prenom, adresse, telephone, email );

        return client;
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

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

}
