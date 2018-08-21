package com.sdzee.tp.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.beans.Fichier;

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

    public Client inscriptionClient( HttpServletRequest req, Fichier fichier ) {

        String choixCliNom = "";
        choixCliNom = req.getParameter( "choixCli" );
        HttpSession session = req.getSession();
        List<Client> listeCli = (List<Client>) session.getAttribute( ATT_SESSION_LISTE_CLIENTS );
        if ( choixCliNom == null )
            choixCliNom = "";
        Client choixCli = new Client( choixCliNom, "", "", "", "", new Fichier( "", "" ) );

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

        /* Initialisation du bean représentant un fichier */
        Fichier fichier1 = new Fichier( "", "" );

        /*
         * Récupération du contenu du champ fichier du formulaire. Il faut ici
         * utiliser la méthode getPart(), comme nous l'avions fait dans notre
         * servlet auparavant.
         */
        String nomFichier = null;
        InputStream contenuFichier = null;

        if ( choixCliNom.equals( "" ) ) {
            try {
                Part part = req.getPart( CHAMP_FICHIER );
                /*
                 * Il faut déterminer s'il s'agit bien d'un champ de type
                 * fichier
                 * 
                 * on délègue cette opération à la méthode utilitaire
                 * getNomFichier().
                 */
                nomFichier = getNomFichier( part );

                /*
                 * Si la demande a renvoyé quelque chose, il s'agit donc d'un
                 * champ de type fichier (input type="file").
                 */
                if ( nomFichier != null && !nomFichier.isEmpty() ) {
                    /*
                     * Antibug pour Internet Explorer, qui transmet pour une
                     * raison mystique le chemin du fichier local à la machine
                     * du client...
                     * 
                     * Ex : C:/dossier/sous-dossier/fichier.ext
                     * 
                     * On doit donc faire en sorte de ne sélectionner que le nom
                     * et l'extension du fichier, et de se débaraser du
                     * superflu.
                     */
                    nomFichier = nomFichier.substring( nomFichier.lastIndexOf( '/' ) + 1 )
                            .substring( nomFichier.lastIndexOf( '\\' ) + 1 );

                    /* Récupération du contenu du fichier */
                    contenuFichier = part.getInputStream();
                }

            } catch ( IllegalStateException e ) {
                /*
                 * Exception retournée si la taille des données dépasse les
                 * limites définies dans la section <multipart-config> de la
                 * déclaration de notre servlet d'upload dans le fichier web.xml
                 */
                e.printStackTrace();
                setErreur( CHAMP_FICHIER, "Les données envoyées sont trop volumineuses." );
            } catch ( IOException e ) {
                /*
                 * Exception retournée si une erreur au niveau des répertoires
                 * de stockage survient (répertoire inexistant, droits d'accès
                 * insuffisants, etc.)
                 */
                e.printStackTrace();
                setErreur( CHAMP_FICHIER, "Erreur de configuration du serveur." );
            } catch ( ServletException e ) {
                /*
                 * Exception retournée si la requête n'est pas de type
                 * multipart/form-data. Cela ne peut arriver que si
                 * l'utilisateur essaie de contacter la servlet d'upload par un
                 * formulaire différent de celui qu'on lui propose... pirate !
                 * :|
                 */
                e.printStackTrace();
                setErreur( CHAMP_FICHIER, "Ce type de requête n'est pas supporté, merci d'utiliser"
                        + " le formulaire prévu pour envoyer votre fichier." );
            }

            /* Si aucune erreur n'est survenue jusqu'à présent */
            if ( erreurs.isEmpty() ) {

                /* Validation du champ fichier. */
                try {
                    validationFichier( nomFichier, contenuFichier );
                } catch ( Exception e ) {
                    setErreur( CHAMP_FICHIER, e.getMessage() );
                }
                fichier1.setNom( nomFichier );

            }

            /* Si aucune erreur n'est survenue jusqu'à présent */
            if ( erreurs.isEmpty() ) {
                /* Ecriture du fichier sur le disque */
                try {
                    ecrireFichier( contenuFichier, nomFichier, fichier.getChemin() );
                } catch ( Exception e ) {
                    setErreur( CHAMP_FICHIER, "Erreur lors de l'écriture du fichier sur le disque." );
                }
            }
        } else {
            fichier1 = fichier;
        }
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

        File f = new File( fichier.getChemin() + nomFichier );

        fichier1.setChemin( f.getAbsolutePath() );

        Client client = new Client( nom, prenom, adresse, telephone, email, fichier1 );

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

    /*
     * Valide le fichier envoyé.
     */
    private void validationFichier( String nomFichier, InputStream contenuFichier ) throws Exception {
        if ( nomFichier == null || contenuFichier == null ) {
            throw new Exception( "Merci de sélectionner un fichier à envoyer." +
                    " nomFichier = " + nomFichier );
        }
    }

    /*
     * Méthode utilitaire qui a pour unique but d'analyser l'en-tête
     * "content-disposition", et de vérifier si le paramètre "filename' y est
     * présent. Si oui, alors le champ traité est de type file et la méthode
     * retourne son nom, sinon il s'agit d'un champ de formulaire classique et
     * la méthode retourne null.
     */
    private static String getNomFichier( Part part ) {
        /*
         * Boucle sur chacun des paramètres de l'en-tête "content-disposition".
         */
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            /* Recherche de l'éventuelle présence du paramètre "filename". */
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                /*
                 * Si "filename" est présent, alors renvoi de sa valeur,
                 * c'est-à-dire du nom de fichier sans guillemets.
                 */
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        /* Et pour terminer, si rien n'a été trouvé... */
        return null;
    }

    /*
     * Méthode utilitaire qui a pour but d'écrire le fichier passé en paramètre
     * sur le disque, dans le répertoire donné et avec le nom donné.
     */
    private void ecrireFichier( InputStream contenu, String nomFichier, String chemin ) throws Exception {
        /* Prépare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux. */
            entree = new BufferedInputStream( contenu, TAILLE_TAMPON );
            sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ),
                    TAILLE_TAMPON );

            /*
             * Lit le fichier reçu et écrit son contenu dans un fichier sur le
             * disque.
             * 
             */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur = 0;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }
        } finally {
            try {
                sortie.close();
            } catch ( IOException ignore ) {

            }
            try {
                entree.close();
            } catch ( IOException ignore ) {

            }
        }
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     * 
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

}
