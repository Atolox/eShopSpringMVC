package eshop;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eshop.dao.DaoAchat;
import eshop.dao.DaoClient;
import eshop.dao.DaoClientJpaImpl;
import eshop.dao.DaoCommande;
import eshop.dao.DaoFournisseur;
import eshop.dao.DaoProduit;
import eshop.entity.Achat;
import eshop.entity.AchatKey;
import eshop.entity.Civilite;
import eshop.entity.Client;
import eshop.entity.Commande;
import eshop.entity.Fournisseur;
import eshop.entity.Produit;
import eshop.util.JpaContext;

public class AppTest {
	public static void main(String[] args) {
		JpaContext.getEntityManagerFactory();
		DaoClient daoClient = new DaoClientJpaImpl();
		DaoFournisseur daoFournisseur = JpaContext.getDaoFournisseur();
		DaoProduit daoProduit = JpaContext.getDaoProduit();
		DaoCommande daoCommande = JpaContext.getDaoCommande();
		DaoAchat daoAchat = JpaContext.getDaoAchat();
		Produit produit1 = new Produit();
		produit1.setLibelle("libelle du produit");
		daoProduit.insert(produit1);
		produit1.setPrix(1000);

		produit1 = daoProduit.update(produit1);

		produit1.setDescription("un produit pour l'exemple");
		produit1 = daoProduit.update(produit1);
		System.out.println(produit1.getVersion());
		
		
		
		JpaContext.DestroyJpaContext();
	}
	
	
	static Produit updateProduit(Produit p) {
		DaoProduit daoProduit = JpaContext.getDaoProduit();
		Produit produitEnBase=daoProduit.findByKey(p.getId());
		produitEnBase.setLibelle(p.getLibelle()==null?produitEnBase.getLibelle():p.getLibelle());
		produitEnBase.setDescription(p.getDescription());
		produitEnBase.setFournisseur(p.getFournisseur());
		produitEnBase.setPrix(p.getPrix());
		
		
		String variable;
		if(p.getLibelle()==null) {
			variable=produitEnBase.getLibelle();
		}else {
			variable=p.getLibelle();
		}
		
		variable=p.getLibelle()==null?produitEnBase.getLibelle():p.getLibelle();
		
		return daoProduit.update(produitEnBase);
//		p.setVersion(produitEnBase.getVersion());
//		return daoProduit.update(p);
	}
}
