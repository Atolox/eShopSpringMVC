package eshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eshop.entity.Adresse;
import eshop.entity.Fournisseur;
import eshop.exception.FournisseurException;
import eshop.exception.IdException;
import eshop.repository.FournisseurRepository;
import eshop.repository.ProduitRepository;

@Service
public class FournisseurService {

	@Autowired
	private FournisseurRepository fournisseurRepo;
	
	//Besoin repository Damien
	@Autowired
	private ProduitRepository produitRepo;

	//OK
	public Fournisseur create(Fournisseur fournisseur) {
		checkFournisseurIsNotNull(fournisseur);
//			if (fournisseur.getcontact() == null || fournisseur.getContact().isEmpty()) {
//				throw new FournisseurException("prenom vide");
//			}
//			if (fournisseur.getNom() == null || fournisseur.getNom().isEmpty()) {
//				throw new FournisseurException("nom vide");
//			}
//			checkFournisseurNomIsNotNullOrEmpty(fournisseur);
//			checkFournisseurContactIsNotNullOrEmpty(fournisseur);
		// on n'a pas mis d'annotation @NotEmpty dans notre entity Fournisseur
		return fournisseurRepo.save(fournisseur);
	}

	//OK
	private void checkFournisseurIsNotNull(Fournisseur fournisseur) {
		if (fournisseur == null) {
			throw new FournisseurException("fournisseur null");
		}
	}

	//OK
	public Fournisseur getById(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return fournisseurRepo.findById(id).orElseThrow(() -> {
			throw new FournisseurException("fournisseur inconnu");
		});
	}

	//souci ici
	
	public Fournisseur getByIdWithProduitsCommeFournisseur(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return fournisseurRepo.findByKeyFetchProduits(id).orElseThrow(() -> {
			throw new FournisseurException("fournisseur inconnu");
		});
	}

	//OK
	public void delete(Fournisseur fournisseur) {
		checkFournisseurIsNotNull(fournisseur);
		deleteById(fournisseur.getId());
	}

	//OK
	public void delete(Long id) {
		deleteById(id);
	}

	//Sortir list produits liée au fournisseur
	//set fournisseur des produits à fournisseur non attribué
	//
	private void deleteById(Long id) {
		Fournisseur fournisseur = getById(id);
		produitRepo.deleteByFournisseur(fournisseur);
		fournisseurRepo.delete(getById(id));
	}

	//OK
	public List<Fournisseur> getAll() {
		return fournisseurRepo.findAll();
	}

	//OK
	public Page<Fournisseur> getAll(Pageable pageable) {
		if (pageable == null) {
			throw new FournisseurException();
		}
		return fournisseurRepo.findAll(pageable);
	}

	//OK
	public Page<Fournisseur> getNextPage(Page<Fournisseur> page) {
		if (page == null) {
			throw new FournisseurException();
		}
		return fournisseurRepo.findAll(page.nextOrLastPageable());
	}

	//OK
	public Page<Fournisseur> getPrevious(Page<Fournisseur> page) {
		if (page == null) {
			throw new FournisseurException();
		}
		return fournisseurRepo.findAll(page.previousOrFirstPageable());
	}

	//OK
	public Fournisseur update(Fournisseur fournisseur) {
		// @formatter:off
			Fournisseur fournisseurEnBase = getById(fournisseur.getId());
			fournisseurEnBase.setContact(fournisseur.getContact() == null||fournisseur.getContact().isEmpty() ? fournisseurEnBase.getContact() : fournisseur.getContact());
			fournisseurEnBase.setNom(fournisseur.getNom() == null||fournisseur.getNom().isEmpty()? fournisseurEnBase.getNom() : fournisseur.getNom());
			fournisseurEnBase.setEmail(fournisseur.getEmail());
			if (fournisseur.getAdresse() != null) {
				fournisseurEnBase.setAdresse(
									new Adresse(
											fournisseur.getAdresse().getNumero(), 
											fournisseur.getAdresse().getRue(),
											fournisseur.getAdresse().getCodePostal(),
											fournisseur.getAdresse().getVille()));
			} else {
				fournisseurEnBase.setAdresse(null);
			}
			return fournisseurRepo.save(fournisseurEnBase);
			// @formatter:on
	}
}
