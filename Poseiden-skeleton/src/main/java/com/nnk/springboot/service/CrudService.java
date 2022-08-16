package com.nnk.springboot.service;

import java.util.List;

public interface CrudService<T> {
	T add(T obj);
	void delete(Integer id);
	List<T> getAll();
	T getById(Integer id);
	T update(Integer id, T obj);
}
