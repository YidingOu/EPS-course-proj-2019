package com.ipv.service;

import java.util.List;


public interface BaseService <E> {
	public List<E> findAll();

	public E findById(int id);

	public E save(E entity);
	
	public void update(E entity);

	public void deleteById(int id);

}
