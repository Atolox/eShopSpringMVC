package eshop.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import eshop.entity.Fournisseur;
import eshop.entity.Produit;
import eshop.util.JpaContext;

public class DaoProduitJpaImpl implements DaoProduit {

	@Override
	public void insert(Produit obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		em.persist(obj);
		tx.commit();
		em.close();
	}

	@Override
	public Produit update(Produit obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx=em.getTransaction();
		Produit produit=null;
		tx.begin();
		produit=em.merge(obj);
		tx.commit();
		em.close();
		return produit;
	}

	@Override
	public void delete(Produit obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		em.remove(em.merge(obj));
		tx.commit();
		em.close();
	}

	@Override
	public void deleteByKey(Long key) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		em.remove(em.find(Produit.class, key));
		tx.commit();
		em.close();
	}

	@Override
	public Produit findByKey(Long key) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		Produit produit=null;
		produit=em.find(Produit.class, key);
		em.close();
		return produit;
	}

	@Override
	public List<Produit> findAll() {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		TypedQuery<Produit> query=em.createQuery("from Produit",Produit.class);
		List<Produit> produits=query.getResultList();
		em.close();
		return produits;
	}
	
	public List<Produit> findByLibelle(String libelle){
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		//jpsql =>meme syntaxe que le sql
		// select from where order by group
		//jpql travaille sur des entites
		//String requeteJpql="select p from Produit p"; =>traduit select * from product
		//"select p from Produit p where p.libelle='velo'";
		//"select p from Produit p where p.libelle like 'velo%'";
		String requeteJpql="select p from Produit p where p.libelle like :libelle";
				
		TypedQuery<Produit> query=em.createQuery(requeteJpql,Produit.class);
		query.setParameter("libelle", libelle);
		List<Produit> produits=query.getResultList();
		em.close();
		return produits;
	}
	
	public void setFournisseurToNull(Fournisseur fournisseur) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		String requeteJpql="update Produit p set p.fournisseur=null where p.fournisseur=:fournisseur";
		Query query=em.createQuery(requeteJpql);
		query.setParameter("fournisseur", fournisseur);
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		query.executeUpdate();
		tx.commit();
		em.close();	
	}
	
	public void deleteByFournisseur(Fournisseur fournisseur) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		String requeteJpql="delete from Produit p where p.fournisseur=:fournisseur";
		Query query=em.createQuery(requeteJpql);
		query.setParameter("fournisseur", fournisseur);
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		query.executeUpdate();
		tx.commit();
		em.close();	
	}
	
}
