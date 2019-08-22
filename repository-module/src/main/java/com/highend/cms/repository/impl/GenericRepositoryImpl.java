package com.highend.cms.repository.impl;

import com.highend.cms.repository.GenericRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {
    protected static final int BATCH_SIZE = 2;
    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericRepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void merge(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public T getById(I id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> getAll() {
        String query = "from " + entityClass.getName() + " c";
        Query q = entityManager.createQuery(query);
        return q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getPage(int page) {
        String query = "from " + entityClass.getName() + " c";
        Query q = entityManager.createQuery(query)
                .setFirstResult(getOffset(page))
                .setMaxResults(BATCH_SIZE);
        return q.getResultList();
    }

    @Override
    public int getCountOfEntities() {
        String query = "SELECT COUNT(*) FROM " + entityClass.getName() + " c";
        Query q = entityManager.createQuery(query);
        return ((Number) q.getSingleResult()).intValue();
    }

    @Override
    public int getCountOfPages() {
        int count = getCountOfEntities();
        return (int) Math.ceil(count / (double) BATCH_SIZE);
    }

    protected int getOffset(int page) {
        return -BATCH_SIZE + page * BATCH_SIZE;
    }
}


