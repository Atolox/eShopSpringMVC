package eshop.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import eshop.entity.Commande;
import eshop.util.JpaContext;

public class DaoCommandeJpaImpl implements DaoCommande {

	@Override
	public void insert(Commande obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		em.persist(obj);
		tx.commit();
		em.close();
	}

	@Override
	public Commande update(Commande obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx=em.getTransaction();
		Commande commande=null;
		tx.begin();
		commande=em.merge(obj);
		tx.commit();
		em.close();
		return commande;
	}

	@Override
	public void delete(Commande obj) {
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
		em.remove(em.find(Commande.class, key));
		tx.commit();
		em.close();
	}

	@Override
	public Commande findByKey(Long key) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		Commande commande=null;
		commande=em.find(Commande.class, key);
		em.close();
		return commande;
	}

	@Override
	public List<Commande> findAll() {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		TypedQuery<Commande> query=em.createQuery("from Commande",Commande.class);
		List<Commande> commandes=query.getResultList();
		em.close();
		return commandes;
	}

	
}
