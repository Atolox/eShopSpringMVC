package eshop.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eshop.entity.Achat;
import eshop.entity.Commande;
import eshop.entity.Produit;

public interface AchatRepository extends JpaRepository<Achat, Long> {

// TODO : Débugger cette fonction
//	Changement des produits en non repertorié dans les achats
//	@Modifying
//	@Transactional
//	@Query("update AchatKey a set a.id.produit=100 where a.id.produit=:produit")
//	void updateByAchatKeySetAchatKeytoNR(@Param("produit")Produit produit);

	// Suppression de tous les achats d'une commande
	@Query("delete Achat a where a.id.commande=:commande")
	@Modifying
	@Transactional
	void deleteByCommande(@Param("commande") Commande commande);
}
