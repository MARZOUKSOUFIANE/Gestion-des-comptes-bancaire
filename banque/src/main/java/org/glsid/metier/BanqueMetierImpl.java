package org.glsid.metier;

import java.util.Date;
import java.util.Optional;

import org.glsid.dao.CompteRepository;
import org.glsid.dao.OperationRepository;
import org.glsid.entities.Compte;
import org.glsid.entities.CompteCourant;
import org.glsid.entities.Operation;
import org.glsid.entities.Retrait;
import org.glsid.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;

	@Override
	public Compte consulterCompte(String codeCpte) {
		Compte cp = compteRepository.getOne(codeCpte);
		if (!compteRepository.findById(codeCpte).isPresent())
			throw new RuntimeException("Compte introuvable");
		return cp;
	}

	@Override
	public void verser(String codeCpte, double montant) {
		// TODO Auto-generated method stub
		Compte cp = consulterCompte(codeCpte);
		Versement v = new Versement(new Date(), montant, cp);
		operationRepository.save(v);
		cp.setSolde(cp.getSolde() + montant);
		compteRepository.save(cp);
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		// TODO Auto-generated method stub
		Compte cp = consulterCompte(codeCpte);
		double facilitesCaisse = 0;
		if (cp instanceof CompteCourant)
			facilitesCaisse = ((CompteCourant) cp).getDecouverte();
		if (cp.getSolde() + facilitesCaisse < montant)
			throw new RuntimeException("Solde insuffisant");
		Retrait v = new Retrait(new Date(), montant, cp);
		operationRepository.save(v);
		cp.setSolde(cp.getSolde() - montant);
		compteRepository.save(cp);
	}

	@Override
	public void verment(String codeCpte1, String codeCpte2, double montant) {
		// TODO Auto-generated method stub
		if(codeCpte1.equals(codeCpte2))
			throw new RuntimeException("impossible d'effectuer le virement sur le meme compte");
		retirer(codeCpte1, montant);
		verser(codeCpte2, montant);
	}

	@Override
	public Page<Operation> listOperation(String codeCpte, int page, int size) {
		// TODO Auto-generated method stub
		return operationRepository.listOperation(codeCpte, new PageRequest(page, size));
	}

}
