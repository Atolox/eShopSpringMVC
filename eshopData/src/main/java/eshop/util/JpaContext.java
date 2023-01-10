package eshop.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import eshop.dao.DaoAchat;
import eshop.dao.DaoAchatJpaImpl;
import eshop.dao.DaoClient;
import eshop.dao.DaoClientJpaImpl;
import eshop.dao.DaoCommande;
import eshop.dao.DaoCommandeJpaImpl;
import eshop.dao.DaoFournisseur;
import eshop.dao.DaoFournisseurJpaImpl;
import eshop.dao.DaoProduit;
import eshop.dao.DaoProduitJpaImpl;

public class JpaContext {
	private static EntityManagerFactory emf = null;
	private static DaoFournisseur daoFournisseur=null;
	private static DaoClient daoClient=null;
	private static DaoProduit daoProduit=null;
	private static DaoCommande daoCommande=null;
	private static DaoAchat daoAchat=null;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("baseAltenPourJPA");
		}
		return emf;
	}

	public static void DestroyJpaContext() {
		if (emf != null) {
			emf.close();
			emf = null;
		}
	}
	
	//methode qui fournit un objet=>fabrique=>pattern factory
	//couplage faible=>pattern facade
	public static DaoFournisseur getDaoFournisseur() {
		if(daoFournisseur==null) {
			daoFournisseur=new DaoFournisseurJpaImpl();
		}
		return daoFournisseur;
	}
	
	public static DaoClient getDaoClient() {
		if(daoClient==null) {
			daoClient=new DaoClientJpaImpl();
		}
		return daoClient;
	}
	
	public static DaoProduit getDaoProduit() {
		if(daoProduit==null) {
			daoProduit=new DaoProduitJpaImpl();
		}
		return daoProduit;
	}
	
	public static DaoCommande getDaoCommande() {
		if(daoCommande==null) {
			daoCommande=new DaoCommandeJpaImpl();
		}
		return daoCommande;
	}
	
	public static DaoAchat getDaoAchat() {
		if(daoAchat==null) {
			daoAchat=new DaoAchatJpaImpl();
		}
		return daoAchat;
	}
}
