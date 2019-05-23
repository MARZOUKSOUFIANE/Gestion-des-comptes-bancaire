package org.glsid.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.glsid.dao.ClientRepository;
import org.glsid.dao.CompteRepository;
import org.glsid.dao.OperationRepository;
import org.glsid.entities.Client;
import org.glsid.entities.Compte;
import org.glsid.entities.CompteCourant;
import org.glsid.entities.CompteEpargne;
import org.glsid.entities.Operation;
import org.glsid.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompteController {
	@Autowired
	IBanqueMetier banqueMetier;

	@Autowired
	ClientRepository clientRepository;
	@Autowired
	CompteRepository compteRepository;
	
	@Autowired
	OperationRepository operationRepository;
	
	@RequestMapping("/comptes")
	public String gererCompte(Model model){
		model.addAttribute("first",true);
		return "GererCompte";
	}


	
	@RequestMapping(value="/ajouterCompte",method=RequestMethod.POST)
	public String ajouterCompte(Model model,Long codeClient, String typeCompte,String codeCompte,double solde,String dateCreation){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		 try {
			 date = formatter.parse(dateCreation);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		Compte compte=null;
		if(typeCompte.equals("Courant")){
			System.out.println("type=>"+typeCompte);
			Optional<Client> client=clientRepository.findById(codeClient);
			compte=new CompteCourant(codeCompte, date, solde, client.get(),6000);		
		}
		else{
			System.out.println("type=>"+typeCompte);
				Optional<Client> client=clientRepository.findById(codeClient);
			
			compte=new CompteEpargne(codeCompte, date, solde,client.get(),12);	
		}
		
		compteRepository.save(compte);
		
	

		return "redirect:/consulterClient?codeClient="+codeClient;
	}

	
	
	
	@RequestMapping(value = "/deleteCompte", method = RequestMethod.GET)
	public String delete(Model model,@RequestParam(name="code") String code,Long codeClient) {
		System.out.println("soufiane marzouk");
		operationRepository.deleteAllOperationByCompte(code);
		compteRepository.deleteById(code);
		return "redirect:/consulterClient?codeClient="+codeClient;
	}

	
@RequestMapping("/consulterClient")
public String consulterClient(Model model, Long codeClient,@RequestParam(name="page",defaultValue="0")int page,@RequestParam(name="size",defaultValue="4")int size){
	model.addAttribute("codeClient",codeClient);
	try {
		//Compte compte=banqueMetier.consulterCompteByCodeClient(codeClient);
		Client client=clientRepository.getOne(codeClient);
		Page<Compte> pageCompte=banqueMetier.listCompte(codeClient, page, size);
		
		model.addAttribute("listComptes",pageCompte.getContent());
		
		model.addAttribute("client",client);
		int [] pages=new int[pageCompte.getTotalPages()];
		model.addAttribute("pages",pages);
		model.addAttribute("pageCourante", page);
		
	} catch (RuntimeException e) {
		model.addAttribute("e",e);
		model.addAttribute("errorMessage","Client introuvalble");
		model.addAttribute("client",null);

	}
	return "GererCompte";
}

}
