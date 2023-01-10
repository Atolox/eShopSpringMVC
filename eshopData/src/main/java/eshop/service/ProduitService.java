package eshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eshop.entity.Produit;
import eshop.exception.IdException;
import eshop.exception.ProduitException;
import eshop.repository.ProduitRepository;

@Service
public class ProduitService {

	@Autowired
	private ProduitRepository produitRepo;
	@Autowired
	private FournisseurService fournisseurService;

	// creation d'un produit
	public Produit create(Produit produit) {
		checkProduitIsNotNull(produit);
		checkProduitLibelleIsNotNullOrEmpty(produit);
		checkFournisseurIsNotNull(produit);
		return produitRepo.save(produit);
	}

	private void checkProduitLibelleIsNotNullOrEmpty(Produit produit) {
		if (produit.getLibelle() == null || produit.getLibelle().isEmpty()) {
			throw new ProduitException("libelle null or empty");
		}
	}

	public void checkProduitIsNotNull(Produit produit) {
		if (produit == null) {
			throw new ProduitException("produit null");
		}
	}

	public void checkFournisseurIsNotNull(Produit produit) {
		if (produit.getFournisseur() == null) {
			throw new ProduitException("fournisseur null");
		}
	}

	// Recherche par Id
	public Produit getById(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return produitRepo.findById(id).orElseThrow(() -> {
			throw new ProduitException("Produit inconnu");
		});
	}

	// Recherche par libelle
	public Produit getByLibelle(String libelle) {
		if (libelle == null) {
			throw new ProduitException();
		}
		return produitRepo.findByLibelle(libelle).orElseThrow(() -> {
			throw new ProduitException("libelle inconnu");
		});
	}

	// Recherche par fournisseur
	//

	// Delete

	public void delete(Produit produit) {
		checkProduitIsNotNull(produit);
		deleteById(produit.getId());
	}

	public void delete(Long id) {
		deleteById(id);
	}

	private void deleteById(Long id) {
		Produit produit = getById(id);
		produitRepo.delete(produit);
	}

	public List<Produit> getAll() {
		return produitRepo.findAll();
	}

	public Page<Produit> getAll(Pageable pageable) {
		if (pageable == null) {
			throw new ProduitException();
		}
		return produitRepo.findAll(pageable);
	}

	public Page<Produit> getNextPage(Page<Produit> page) {
		if (page == null) {
			throw new ProduitException();
		}
		return produitRepo.findAll(page.nextOrLastPageable());
	}

	public Page<Produit> getPrevious(Page<Produit> page) {
		if (page == null) {
			throw new ProduitException();
		}
		return produitRepo.findAll(page.previousOrFirstPageable());
	}

	public Produit update(Produit produit) {
		Produit produitEnBase = getById(produit.getId());
		checkProduitLibelleIsNotNullOrEmpty(produitEnBase);
		produitEnBase.setLibelle(produit.getLibelle());
		checkFournisseurIsNotNull(produitEnBase);
		produitEnBase.setDescription(produit.getDescription());
		produitEnBase.setFournisseur(fournisseurService.getById(produit.getFournisseur().getId()));
		return produitRepo.save(produitEnBase);
	}

}
