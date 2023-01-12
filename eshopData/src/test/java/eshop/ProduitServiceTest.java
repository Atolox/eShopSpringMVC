package eshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import eshop.config.JpaConfig;
import eshop.entity.Produit;
import eshop.repository.ProduitRepository;
import eshop.service.ProduitService;

@SpringJUnitConfig(JpaConfig.class)
class ProduitServiceTest {
	
	@Autowired
	private ProduitRepository produitRepo;
	@Autowired
	private ProduitService produitService;
	
	@Test
	void create() {
		Produit nr = new Produit("Non repertorié","Le produit n'est plus dans notre BDD", 0);
		produitService.create(nr);
	}
	
	@Test
	void getBy() {
		System.out.println(produitService.getById(100L));
		System.out.println(produitService.getByLibelle("Non"));
	}
	
	@Test
	void update() {
		Produit p1 = new Produit("ABC","Best Product of the year", 0);
		produitService.create(p1);
		p1.setLibelle("XYZ");
		produitService.update(p1);
		System.out.println(p1.getLibelle());
		
	}
	

}
