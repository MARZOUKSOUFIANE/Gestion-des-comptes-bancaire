package org.glsid.entities;

import java.util.Date;

public class CompteCourant extends Compte{

	private double decouverte;

	public CompteCourant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompteCourant(String codeCompte, Date dateCreation, double solde, Client client, double decouverte) {
		super(codeCompte, dateCreation, solde, client);
		this.decouverte = decouverte;
	}

	public double getDecouverte() {
		return decouverte;
	}

	public void setDecouverte(double decouverte) {
		this.decouverte = decouverte;
	}

	
}
