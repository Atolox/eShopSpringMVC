package eshop.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import eshop.entity.Achat;
import eshop.entity.AchatKey;
import eshop.util.JpaContext;

public class DaoAchatJpaImpl implements DaoAchat {

	@Override
	public void insert(Achat obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		em.persist(obj);
		tx.commit();
		em.close();
	}

	@Override
	public Achat update(Achat obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx=em.getTransaction();
		Achat achat=null;
		tx.begin();
		achat=em.merge(obj);
		tx.commit();
		em.close();
		return achat;
	}

	@Override
	public void delete(Achat obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		em.remove(em.merge(obj));
		tx.commit();
		em.close();
	}

	@Override
	public void deleteByKey(AchatKey key) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		em.remove(em.find(Achat.class, key));
		tx.commit();
		em.close();
	}

	@Override
	public Achat findByKey(AchatKey key) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		Achat achat=null;
		achat=em.find(Achat.class, key);
		em.close();
		return achat;
	}

	@Override
	public List<Achat> findAll() {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		TypedQuery<Achat> query=em.createQuery("from Achat",Achat.class);
		List<Achat> achats=query.getResultList();
		em.close();
		return achats;
	}

	
}
