package org.glsid.entities;

import java.io.Serializable;
import java.util.Collection;

public class Client implements Serializable {

	private Long code;
	private String nom;
	private String email;
	private Collection<Compte> comptes;

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(String nom, String email) {
		super();
		this.nom = nom;
		this.email = email;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(Collection<Compte> comptes) {
		this.comptes = comptes;
	}

}
