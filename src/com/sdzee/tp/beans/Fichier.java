package com.sdzee.tp.beans;

public class Fichier {

    private String chemin;
    private String nom;

    public Fichier( String chemin, String nom ) {
        super();
        this.chemin = chemin;
        this.nom = nom;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin( String chemin ) {
        this.chemin = chemin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

}
