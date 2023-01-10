package eshop;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import eshop.config.JpaConfig;
import eshop.entity.Fournisseur;
import eshop.exception.FournisseurException;
import eshop.repository.FournisseurRepository;

@SpringJUnitConfig(JpaConfig.class)
class FournisseurRepositoryTest {

	@Autowired
	private FournisseurRepository fournisseurRepo;
	
	@Test
	void requetePerso() {
		fournisseurRepo.findByEmail("aaa@bbb.com");
		fournisseurRepo.findByNomContaining("o");
		
	}

	@Test
	@Disabled
	void test() {
		Fournisseur f = fournisseurRepo.findById(100L).orElseThrow(FournisseurException::new);
		assertTrue(f instanceof Fournisseur);
//		if (opt.isPresent()) {
//			System.out.println(opt.get());
//		}
	}

	@Test
	@Disabled
	void findByIdException() {
		assertThrows(FournisseurException.class, () -> {
			fournisseurRepo.findById(500L).orElseThrow(FournisseurException::new);
		});
	}

	@Test
	@Disabled
	void PageTest() {
		Pageable pageable = PageRequest.of(0, 20);
		Page<Fournisseur> page = fournisseurRepo.findAll(pageable);
		System.out.println(page);
		page.forEach(f -> {
			System.out.println(f.getId());
		});
		page = fournisseurRepo.findAll(page.nextOrLastPageable());
		System.out.println(page);
		page.forEach(f -> {
			System.out.println(f.getId());
		});
	}
	
	

}
