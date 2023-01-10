package eshop.dao;

import java.util.List;

public interface DaoGeneric<T,K> {
	void insert(T obj);
	T update(T obj);
	void delete(T obj);
	void deleteByKey(K key);
	T findByKey(K key);
	List<T> findAll();
}
