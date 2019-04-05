package com.ipv.service;

import java.util.List;

/**
 * 
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 * The actual service interfaces will get the methods by extends this BaseService
 * The E is the Entity type of the service
 * 
 */
public interface BaseService <E> {
	public List<E> findAll();

	public E findById(int id);

	public E save(E entity);
	
	public void update(E entity);

	public void deleteById(int id);

}
