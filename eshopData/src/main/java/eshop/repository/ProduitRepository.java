package eshop.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import formation.entity.Formateur;

public class ProduitRepository {
	
	List<Produit> findByLibelle(String libelle); //recherche par nom de produit
	List<Produit> findByLibelleContaining(String libelle); //recherche par
	Page<Produit> findByLibelleContaining(String nom, Pageablee pageable);
	

	@Modifying
	@Transactional
	@Query("update Produit p set p.fournisseur=null where p.fournisseur=:fournisseur")
	void updateByFournisseurSetFournisseurToNull(@Param("fournisseur") Produit fournisseur);

}
