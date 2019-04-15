package org.glsid;

import java.util.Date;

import org.glsid.dao.ClientRepository;
import org.glsid.dao.CompteRepository;
import org.glsid.dao.OperationRepository;
import org.glsid.entities.Client;
import org.glsid.entities.Compte;
import org.glsid.entities.CompteCourant;
import org.glsid.entities.CompteEpargne;
import org.glsid.entities.Operation;
import org.glsid.entities.Retrait;
import org.glsid.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;

@SpringBootApplication
public class BanqueApplication implements CommandLineRunner{
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	CompteRepository compteRepository;
	@Autowired
	OperationRepository operationRepository;
	public static void main(String[] args) {
		SpringApplication.run(BanqueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Client c1= clientRepository.save(new Client("Hassan", "hassan@gmail.com"));
		Client c2= clientRepository.save(new Client("Ahmed", "Ahmed@gmail.com"));
		
		Compte cp1=compteRepository.save(new CompteCourant("c1",new Date(),9000,c1,6000));
		Compte cp2=compteRepository.save(new CompteEpargne("c2",new Date(),6000,c2,5.5));
		
		Operation op1=operationRepository.save(new Versement(new Date(), 9000, cp1));
		Operation op2=operationRepository.save(new Retrait(new Date(), 4000, cp1));
		Operation op3=operationRepository.save(new Versement(new Date(), 3500, cp2));
		Operation op4=operationRepository.save(new Retrait(new Date(), 1500, cp2));

	}
}
