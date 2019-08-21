package com.highend.cms.repository;

import java.util.List;

public interface GenericRepository<I, T> {

    void persist(T entity);

    void merge(T entity);

    T getById(I id);

    List<T> getAll();

    List<T> getPage(int page);

    int getCountOfEntities();

    int getCountOfPages();
}
