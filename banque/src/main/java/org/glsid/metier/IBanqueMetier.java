package org.glsid.metier;

import org.glsid.entities.Compte;
import org.glsid.entities.Operation;
import org.springframework.data.domain.Page;

public interface IBanqueMetier {

	public Compte consulterCompte(String codeCpte);
	public void verser(String codeCpte,double montant);
	public void retirer(String codeCpte,double montant);
	public void verment(String codeCpte1,String codeCpte2,double montant);
	public Page<Operation> listOperation(String codeCpte,int page,int size);
}
