package com.ipv.service.imple;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public class BaseImple <E> {

	public JpaRepository<E, Integer> repository;

	public List<E> findAll() {
		return repository.findAll();
	}

	public E findById(int id){
		Optional<E> result = repository.findById(id);

		E entity = null;
		if (result.isPresent()) {
			entity = result.get();
		}
		return entity;
	}

	public E save(E entity){
		return repository.save(entity);
	}

	public void update(E entity){
		repository.save(entity);
	}

	public void deleteById(int id){
		repository.deleteById(id);
	}

}
