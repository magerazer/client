package com.sdzee.tp.forms;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class UploadForm {

    private static final String CHAMP_FICHIER     = "fichier";
    private static final int    TAILLE_TAMPON     = 10240;
    private static final String CHAMP_DESCRIPTION = "description";

    private String              resultat;
    private Map<String, String> erreurs           = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    //
    // public Fichier enregistrerFichier( HttpServletRequest request, String
    // chemin ) {
    //
    // /* Si aucune erreur n'est survenue jusqu'à présent
    // */
    // if(erreurs.isEmpty())
    //
    // {
    // /* Validation du champ de description */
    // try {
    // validationDescription( description );
    // } catch ( Exception e ) {
    // setErreur( CHAMP_DESCRIPTION, e.getMessage() );
    // }
    // fichier.setDescription( description );
    //
    // /* Validation du champ fichier. */
    // try {
    // validationFichier( nomFichier, contenuFichier );
    // } catch ( Exception e ) {
    // setErreur( CHAMP_FICHIER, e.getMessage() );
    // }
    // fichier.setNom( nomFichier );
    //
    // }
    //
    // /* Si aucune erreur n'est survenue jusqu'à présent */
    // if(erreurs.isEmpty())
    // {
    // /* Ecriture du fichier sur le disque */
    // try {
    // ecrireFichier( contenuFichier, nomFichier, chemin );
    // } catch ( Exception e ) {
    // setErreur( CHAMP_FICHIER, "Erreur lors de l'écriture du fichier sur le
    // disque." );
    // }
    // }
    //
    // /* Initialisation du résultat global de la validation. */
    // if(erreurs.isEmpty())
    // {
    // resultat = "Succès de l'envoi du fichier.";
    // }else
    // {
    // resultat = "Echec de l'envoi du fichier.";
    // }
    //
    // return fichier;
    // }
    //

    /*
     * Valide la description saisie.
     */
    private void validationDescription( String description ) throws Exception {
        if ( description != null ) {
            if ( description.length() < 15 ) {
                throw new Exception( "La phrase de description du fichier doit contenir au moins 15 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer une phrase de description du fichier." );
        }
    }

    /*
     * Valide le fichier envoyé.
     */
    private void validationFichier( String nomFichier, InputStream contenuFichier ) throws Exception {
        if ( nomFichier == null || contenuFichier == null ) {
            throw new Exception( "Merci de sélectionner un fichier à envoyer." );
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     * 
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }

}
