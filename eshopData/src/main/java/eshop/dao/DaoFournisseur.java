package eshop.dao;

import eshop.entity.Fournisseur;

public interface DaoFournisseur extends DaoGeneric<Fournisseur, Long>{
	public Fournisseur findByKeyFetchProduits(Long key);
}
