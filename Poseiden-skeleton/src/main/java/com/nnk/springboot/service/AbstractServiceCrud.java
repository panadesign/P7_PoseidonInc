package com.nnk.springboot.service;

import com.nnk.springboot.exception.ResourceNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.List;


@RequiredArgsConstructor
@Component
public abstract class AbstractServiceCrud<T, R extends JpaRepository<T, Integer>> implements CrudService<T> {
	
	protected final R repository;
	
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
