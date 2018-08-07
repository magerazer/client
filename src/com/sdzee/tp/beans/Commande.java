package com.sdzee.tp.beans;

public class Commande {

	private Client client;
	private String date = "";
	private double montant;
	private String modePaiement;
	private String statutPaiement;
	private String modeLivraison;
	private String statutLivraison;
	
	public Commande(Client client, String date, double montant, String modePaiement, String statutPaiement, String modeLivraison, String statutLivraison) {
		this.client = client;
		this.date = date;
		this.montant = montant;
		this.modePaiement = modePaiement;
		this.statutPaiement = statutPaiement;
		this.modeLivraison = modeLivraison;
		this.statutLivraison = statutLivraison;				
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	public String getStatutLivraison() {
		return statutLivraison;
	}

	public void setStatutLivraison(String statutLivraison) {
		this.statutLivraison = statutLivraison;
	}

	public String getStatutPaiement() {
		return statutPaiement;
	}

	public void setStatutPaiement(String statutPaiement) {
		this.statutPaiement = statutPaiement;
	}

	public String getModeLivraison() {
		return modeLivraison;
	}

	public void setModeLivraison(String modeLivraison) {
		this.modeLivraison = modeLivraison;
	}
	
	
}
