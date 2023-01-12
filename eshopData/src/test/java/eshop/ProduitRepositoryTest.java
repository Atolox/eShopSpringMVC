package eshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import eshop.repository.ProduitRepository;
import eshop.config.JpaConfig;

@SpringJUnitConfig(JpaConfig.class)
class ProduitRepositoryTest {
	
	
	@Autowired
	private ProduitRepository produitRepo;
	
	@Test
	void requetePerso() {
		produitRepo.findByLibelleContaining("c");
		
		
	}
	

}
