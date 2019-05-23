package org.glsid.dao;

import org.glsid.entities.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompteRepository extends JpaRepository<Compte, String> {
	@Query("select c from Compte c where c.client.code=:x")
		public Compte  consulterCompteByCodeClient(@Param("x") Long code);
	
	 @Query("select c from Compte c where c.client.code=:x order by c.dateCreation desc")
		public Page<Compte> listCompte(@Param("x")Long codeClient,Pageable pageable);
}
