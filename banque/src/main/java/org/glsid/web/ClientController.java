package org.glsid.web;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.glsid.dao.ClientRepository;
import org.glsid.entities.Client;
import org.glsid.entities.Compte;
import org.glsid.entities.Operation;
import org.glsid.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Transactional
public class ClientController {
	@Autowired
	IBanqueMetier banqueMetier;

	@Autowired
	ClientRepository clientRepository;

	
	
@RequestMapping("/clients")
	public String cients(Model model,@RequestParam(name="page",defaultValue="0")int page,@RequestParam(name="size",defaultValue="4")int size){
	
	Client client=new Client();
	model.addAttribute("client",client);
		
		
		try {
			
			Page<Client> pageClient=banqueMetier.listClient(page, size);
			
			model.addAttribute("listClients",pageClient.getContent());
			
			
			int [] pages=new int[pageClient.getTotalPages()];
			model.addAttribute("pages",pages);
			model.addAttribute("pageCourante", page);
			
		} catch (RuntimeException e) {
			model.addAttribute("e",e);
			model.addAttribute("errorMessage","Compte introuvalble");
			System.err.println(e.getMessage());;
			model.addAttribute("compte",null);

		}
		
		return "clients";
		
		
		
	}



@RequestMapping(value = "/deleteClient", method = RequestMethod.GET)
public String delete(Model model,@RequestParam(name="code") Long code) {
	clientRepository.deleteById(code);
	return "redirect:/clients";
}


@RequestMapping(value = "/editClient", method = RequestMethod.GET)
public String editClient(Model model,Long code,@RequestParam(name="page",defaultValue="0")int page,@RequestParam(name="size",defaultValue="4")int size) {
	Optional<Client> client=clientRepository.findById(code);
	
	if(client.get()!=null){
		model.addAttribute("client", client.get());
	
		
		Page<Client> pageClient=banqueMetier.listClient(page, size);
		
		model.addAttribute("listClients",pageClient.getContent());
	}
		
	else{
		return "redirect:/clients";
	}
	
	return "clients";
}



@RequestMapping(value = "/saveClient", method = RequestMethod.POST)
public String saveReclamation(Model model, @Valid Client client, BindingResult bindingResult) {
	if (bindingResult.hasErrors()) {
		return "clients";
	}
	clientRepository.save(client);
	return "redirect:/clients";
}




}
