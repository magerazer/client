package com.sdzee.tp.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Client;

public class CreationClientForm {

    public static final String  CHAMP_NOM                 = "nomClient";
    public static final String  CHAMP_PRENOM              = "prenomClient";
    public static final String  CHAMP_ADR                 = "adresseClient";
    public static final String  CHAMP_TEL                 = "telephoneClient";
    public static final String  CHAMP_MAIL                = "emailClient";
    private static final String CHAMP_FICHIER             = "fichier";
    private static final int    TAILLE_TAMPON             = 10240;

    private static final String ATT_ERREURS               = "erreurs";
    private static final String ATT_RESULTAT              = "resultatClient";

    private String              resultat;
    private Map<String, String> erreurs                   = new HashMap<String, String>();

    public static final String  ATT_SESSION_LISTE_CLIENTS = "listeSessionClients";

    public Client inscriptionClient( HttpServletRequest req ) {

        String choixCliNom = "";
        choixCliNom = req.getParameter( "choixCli" );
        HttpSession session = req.getSession();
        List<Client> listeCli = (List<Client>) session.getAttribute( ATT_SESSION_LISTE_CLIENTS );
        if ( choixCliNom == null )
            choixCliNom = "";
        Client choixCli = new Client( choixCliNom, "", "", "", "" );

        if ( listeCli == null ) {
            System.out.println( "Hello" );
            listeCli = new ArrayList<Client>();
        }
        System.out.println( "HEEEEEEEEEELLLLLLLLLLLLOOOOOOOOOO !!!!!!!" );
        System.out.println( "choixCliNom " + choixCliNom + "." );
        for ( int i = 0; i < listeCli.size(); i++ ) {
            System.out.println( listeCli.get( i ).getNom() + "." );
        }

        if ( listeCli != null ) {
            // if ( listeCli.contains( choixCli ) ) {
            System.out.println( "euh" );
            for ( int i = 0; i < listeCli.size(); i++ ) {
                if ( listeCli.get( i ).equals( choixCli ) ) {
                    choixCli = listeCli.get( i );
                    System.out.println( "euh" );
                } else {
                    System.out.println( "beeh" );
                }

            }
            // }
        }

        String nom, prenom, adresse, telephone, email;

        nom = choixCliNom.equals( "" ) ? req.getParameter( CHAMP_NOM ) : choixCli.getNom();
        prenom = choixCliNom.equals( "" ) ? req.getParameter( CHAMP_PRENOM ) : choixCli.getPrenom();
        adresse = choixCliNom.equals( "" ) ? req.getParameter( CHAMP_ADR ) : choixCli.getAdresse();
        telephone = choixCliNom.equals( "" ) ? req.getParameter( CHAMP_TEL ) : choixCli.getTelephone();
        email = choixCliNom.equals( "" ) ? req.getParameter( CHAMP_MAIL ) : choixCli.getEmail();

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
