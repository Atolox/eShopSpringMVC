package eshop.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import eshop.entity.Fournisseur;
import eshop.util.JpaContext;

public class DaoFournisseurJpaImpl implements DaoFournisseur {

	@Override
	public void insert(Fournisseur obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(obj);
		tx.commit();
		em.close();
	}

	@Override
	public Fournisseur update(Fournisseur obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Fournisseur fournisseur = null;
		tx.begin();
		fournisseur = em.merge(obj);
		tx.commit();
		em.close();
		return fournisseur;
	}

	@Override
	public void delete(Fournisseur obj) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(em.merge(obj));
		tx.commit();
		em.close();
	}

	@Override
	public void deleteByKey(Long key) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(em.find(Fournisseur.class, key));
		tx.commit();
		em.close();
	}

	@Override
	public Fournisseur findByKey(Long key) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		Fournisseur fournisseur = null;
		fournisseur = em.find(Fournisseur.class, key);
//		System.out.println("--------findByKey----------");
//		System.out.println(fournisseur.getProduits());
		em.close();
		return fournisseur;
	}

	@Override
	public List<Fournisseur> findAll() {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		TypedQuery<Fournisseur> query = em.createQuery("from Fournisseur", Fournisseur.class);
		List<Fournisseur> fournisseurs = query.getResultList();
		em.close();
		return fournisseurs;
	}

	public Fournisseur findByKeyFetchProduits(Long key) {
		EntityManager em = JpaContext.getEntityManagerFactory().createEntityManager();
		TypedQuery<Fournisseur> query = em.createQuery(
				"select f from Fournisseur f left join fetch f.produits where f.id=:key", Fournisseur.class);
		query.setParameter("key", key);
		Fournisseur fournisseur = null;
		try {
			fournisseur = query.getSingleResult();
		} catch (NoResultException e) {

		}
		em.close();
		return fournisseur;

	}

}
