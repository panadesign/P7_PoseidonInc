package com.nnk.springboot.service;

import java.util.List;

/**
 * The interface Crud service.
 *
 * @param <T> the type parameter
 */
public interface CrudService<T> {
    /**
     * Add t.
     *
     * @param obj the obj
     * @return the t
     */
    T add(T obj);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Integer id);

    /**
     * Gets all.
     *
     * @return the all
     */
    List<T> getAll();

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    T getById(Integer id);

    /**
     * Update t.
     *
     * @param id  the id
     * @param obj the obj
     * @return the t
     */
    T update(Integer id, T obj);
}
