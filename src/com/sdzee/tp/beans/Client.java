package com.sdzee.tp.beans;

public class Client implements Comparable<Client> {

    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;

    public Client( String nom, String prenom, String adresse, String telephone, String email ) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom( String prenom ) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse( String adresse ) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone( String telephone ) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    @Override
    public int compareTo( Client c ) {

        if ( this.getNom().equals( c.getNom() ) )
            return 0;
        else
            return -1;
    }

    public boolean equals( Client c ) {
        if ( this.getNom().equals( c.getNom() ) )
            return true;
        else
            return false;
    }

}
