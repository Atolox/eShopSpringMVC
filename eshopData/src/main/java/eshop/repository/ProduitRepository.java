package eshop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eshop.entity.Fournisseur;
import eshop.entity.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

	List<Produit> findByLibelle(String libelle); // recherche par nom de produit

	List<Produit> findByLibelleContaining(String libelle); // recherche par

	Page<Produit> findByLibelleContaining(String nom, Pageable pageable);
	
	List<Produit> findByFournisseur(Fournisseur fournisseur);
	

	@Modifying
	@Transactional
	@Query("update Produit f set f.fournisseur=null where f.fournisseur=:fournisseur")
	void updateByFournisseurSetFournisseurToNull(@Param("fournisseur") Fournisseur fournisseur);

	@Modifying
	@Transactional
	@Query("delete Produit f where f.fournisseur=:fournisseur")
	void deleteByFournisseur(@Param("fournisseur") Fournisseur fournisseur);
}
