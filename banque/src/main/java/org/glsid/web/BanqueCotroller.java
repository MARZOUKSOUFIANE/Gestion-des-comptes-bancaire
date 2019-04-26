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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BanqueCotroller {
    @Autowired
	private IBanqueMetier banqueMetier;
	
    @RequestMapping("/operations")
    public String index() {
    	return "comptes";
    }
    
    
    @RequestMapping("/consulterCompte")
    public String consulter(Model model,String codeCompte,
    		@RequestParam(name="page",defaultValue="0") int page,
    		@RequestParam(name="size",defaultValue="5") int size) {
    	try {
    		int pageCourante=page;
    		Compte cp=banqueMetier.consulterCompte(codeCompte);
        	model.addAttribute("compte", Hibernate.unproxy( cp ));
        	model.addAttribute("codeCompte", codeCompte);
        	Page<Operation> pageOperation=banqueMetier.listOperation(codeCompte, page, size);
        	int[]  pages=new int[pageOperation.getTotalPages()];
        	model.addAttribute("pages", pages);
        	model.addAttribute("listeOperations", pageOperation.getContent());
        	model.addAttribute("pageCourante", pageCourante);
		} catch (Exception e) {
			model.addAttribute("exception", e.getMessage());
		}
    	return "comptes";
    }
   
    @RequestMapping(value="/saveOperation", method=RequestMethod.POST)
    public String saveOperation(Model model, String typeOperation, String codeCompte, double montant,String codeCompte2) {
    	try {
    		if(typeOperation.equals("VERS"))
        		banqueMetier.verser(codeCompte, montant);
        	else if(typeOperation.equals("RETR"))
        		banqueMetier.retirer(codeCompte, montant);
        	else
        		banqueMetier.verment(codeCompte, codeCompte2, montant);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
	    	return "redirect:/consulterCompte?codeCompte="+codeCompte+"&error="+e.getMessage();
		}
    	
    	return "redirect:/consulterCompte?codeCompte="+codeCompte;
    }
}
