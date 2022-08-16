package com.nnk.springboot.service;

import com.nnk.springboot.exception.ResourceNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.util.List;


@RequiredArgsConstructor
public abstract class AbstractServiceCrud<T, R extends JpaRepository<T, Integer>> implements CrudService<T> {
	
	protected final R repository;
	
	public abstract T add(T obj);
	
	public abstract T update(Integer id, T obj);
	
	@Override
	public void delete(Integer id) {
		repository.deleteById(id);
	}
	
	@Override
	public List<T> getAll() {
		return repository.findAll();
	}
	
	@Override
	public T getById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotExistException("Resource " + getObjectType() + "not found with id = " + id));
	}
	
	private String getObjectType(){
		return ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
				.getTypeName();
	}
}
