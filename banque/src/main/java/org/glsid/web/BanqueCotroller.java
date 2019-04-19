package org.glsid.web;

import org.glsid.entities.Compte;
import org.glsid.entities.Operation;
import org.glsid.metier.IBanqueMetier;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BanqueCotroller {
    @Autowired
	private IBanqueMetier banqueMetier;
	
    @RequestMapping("/operations")
    public String index() {
    	return "comptes";
    }
    
    
    @RequestMapping("/consulterCompte")
    public String consulter(Model model,String codeCompte) {
    	try {
    		Compte cp=banqueMetier.consulterCompte(codeCompte);
        	model.addAttribute("compte", Hibernate.unproxy( cp ));
        	model.addAttribute("codeCompte", codeCompte);
        	Page<Operation> pageOperation=banqueMetier.listOperation(codeCompte, 0, 5);
        	model.addAttribute("listeOperations", pageOperation.getContent());
        	model.addAttribute("compteType", Hibernate.unproxy( cp ));
		} catch (Exception e) {
			model.addAttribute("exception", e.getMessage());
		}
    	return "comptes";
    }
}
