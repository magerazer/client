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

        /* Initialisation du bean repr�sentant un fichier */
        Fichier fichier1 = new Fichier( "", "" );

        /*
         * R�cup�ration du contenu du champ fichier du formulaire. Il faut ici
         * utiliser la m�thode getPart(), comme nous l'avions fait dans notre
         * servlet auparavant.
         */
        String nomFichier = null;
        InputStream contenuFichier = null;

        if ( choixCliNom.equals( "" ) ) {
            try {
                Part part = req.getPart( CHAMP_FICHIER );
                /*
                 * Il faut d�terminer s'il s'agit bien d'un champ de type
                 * fichier
                 * 
                 * on d�l�gue cette op�ration � la m�thode utilitaire
                 * getNomFichier().
                 */
                nomFichier = getNomFichier( part );

                /*
                 * Si la demande a renvoy� quelque chose, il s'agit donc d'un
                 * champ de type fichier (input type="file").
                 */
                if ( nomFichier != null && !nomFichier.isEmpty() ) {
                    /*
                     * Antibug pour Internet Explorer, qui transmet pour une
                     * raison mystique le chemin du fichier local � la machine
                     * du client...
                     * 
                     * Ex : C:/dossier/sous-dossier/fichier.ext
                     * 
                     * On doit donc faire en sorte de ne s�lectionner que le nom
                     * et l'extension du fichier, et de se d�baraser du
                     * superflu.
                     */
                    nomFichier = nomFichier.substring( nomFichier.lastIndexOf( '/' ) + 1 )
                            .substring( nomFichier.lastIndexOf( '\\' ) + 1 );

                    /* R�cup�ration du contenu du fichier */
                    contenuFichier = part.getInputStream();
                }

            } catch ( IllegalStateException e ) {
                /*
                 * Exception retourn�e si la taille des donn�es d�passe les
                 * limites d�finies dans la section <multipart-config> de la
                 * d�claration de notre servlet d'upload dans le fichier web.xml
                 */
                e.printStackTrace();
                setErreur( CHAMP_FICHIER, "Les donn�es envoy�es sont trop volumineuses." );
            } catch ( IOException e ) {
                /*
                 * Exception retourn�e si une erreur au niveau des r�pertoires
                 * de stockage survient (r�pertoire inexistant, droits d'acc�s
                 * insuffisants, etc.)
                 */
                e.printStackTrace();
                setErreur( CHAMP_FICHIER, "Erreur de configuration du serveur." );
            } catch ( ServletException e ) {
                /*
                 * Exception retourn�e si la requ�te n'est pas de type
                 * multipart/form-data. Cela ne peut arriver que si
                 * l'utilisateur essaie de contacter la servlet d'upload par un
                 * formulaire diff�rent de celui qu'on lui propose... pirate !
                 * :|
                 */
                e.printStackTrace();
                setErreur( CHAMP_FICHIER, "Ce type de requ�te n'est pas support�, merci d'utiliser"
                        + " le formulaire pr�vu pour envoyer votre fichier." );
            }

            /* Si aucune erreur n'est survenue jusqu'� pr�sent */
            if ( erreurs.isEmpty() ) {

                /* Validation du champ fichier. */
                try {
                    validationFichier( nomFichier, contenuFichier );
                } catch ( Exception e ) {
                    setErreur( CHAMP_FICHIER, e.getMessage() );
                }
                fichier1.setNom( nomFichier );

            }

            /* Si aucune erreur n'est survenue jusqu'� pr�sent */
            if ( erreurs.isEmpty() ) {
                /* Ecriture du fichier sur le disque */
                try {
                    ecrireFichier( contenuFichier, nomFichier, fichier.getChemin() );
                } catch ( Exception e ) {
                    setErreur( CHAMP_FICHIER, "Erreur lors de l'�criture du fichier sur le disque." );
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
            resultat = "Succ�s de la cr�ation du client.";
        } else {
            resultat = "Echec de la cr�ation du client.";
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
                throw new Exception( "Le nom doit contenir au moins 2 caract�res." );
            }
        }
    }

    private void validationPrenom( String prenom ) throws Exception {
        if ( !prenom.isEmpty() && prenom.length() < 2 ) {
            throw new Exception( "Le prenom doit contenir au moins 2 caract�res." );
        }

    }

    private void validationTel( String telephone ) throws Exception {
        if ( telephone.isEmpty() ) {
            throw new Exception( "Veuillez renseigner le num�ro de t�l�phone." );
        } else {
            if ( telephone.length() < 4 ) {
                throw new Exception( "Le num�ro doit contenir au moins 4 chiffres." );
            } else {
                if ( !telephone.matches( "[0-9]+" ) ) {
                    throw new Exception( "Le num�ro doit contenir que des chiffres." );
                }
            }
        }
    }

    private void validationAdresse( String adresse ) throws Exception {
        if ( adresse.isEmpty() ) {
            throw new Exception( "Veuillez renseigner l'adresse" );
        } else {
            if ( adresse.length() < 10 ) {
                throw new Exception( "L'adresse doit contenir au moins 10 caract�res." );
            }
        }
    }

    private void validationEmail( String email ) throws Exception {
        if ( !email.isEmpty() && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }

    /*
     * Valide le fichier envoy�.
     */
    private void validationFichier( String nomFichier, InputStream contenuFichier ) throws Exception {
        if ( nomFichier == null || contenuFichier == null ) {
            throw new Exception( "Merci de s�lectionner un fichier � envoyer." +
                    " nomFichier = " + nomFichier );
        }
    }

    /*
     * M�thode utilitaire qui a pour unique but d'analyser l'en-t�te
     * "content-disposition", et de v�rifier si le param�tre "filename' y est
     * pr�sent. Si oui, alors le champ trait� est de type file et la m�thode
     * retourne son nom, sinon il s'agit d'un champ de formulaire classique et
     * la m�thode retourne null.
     */
    private static String getNomFichier( Part part ) {
        /*
         * Boucle sur chacun des param�tres de l'en-t�te "content-disposition".
         */
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            /* Recherche de l'�ventuelle pr�sence du param�tre "filename". */
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                /*
                 * Si "filename" est pr�sent, alors renvoi de sa valeur,
                 * c'est-�-dire du nom de fichier sans guillemets.
                 */
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        /* Et pour terminer, si rien n'a �t� trouv�... */
        return null;
    }

    /*
     * M�thode utilitaire qui a pour but d'�crire le fichier pass� en param�tre
     * sur le disque, dans le r�pertoire donn� et avec le nom donn�.
     */
    private void ecrireFichier( InputStream contenu, String nomFichier, String chemin ) throws Exception {
        /* Pr�pare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux. */
            entree = new BufferedInputStream( contenu, TAILLE_TAMPON );
            sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ),
                    TAILLE_TAMPON );

            /*
             * Lit le fichier re�u et �crit son contenu dans un fichier sur le
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
     * Ajoute un message correspondant au champ sp�cifi� � la map des erreurs.
     * 
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

}
