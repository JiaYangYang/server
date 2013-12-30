package com.youthclub.persister;

import com.youthclub.lookup.LookUp;
import com.youthclub.model.EntityBase;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.List;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
public abstract class AbstractPersister<T extends EntityBase<T>> implements java.io.Serializable {

    protected EntityManager entityManager;
    private Class<T> entityClass;

    public AbstractPersister(Class<T> entityClass) {
        this.entityClass = entityClass;
        entityManager = LookUp.getEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void save(List<T> entities) {
        for (T entity : entities) {
            if (entity.getId() != 0) {
                edit(entity);
            } else {
                create(entity);
            }
        }
    }

    public void save(T entity) {
        if (entity.getId() != 0) {
            edit(entity);
        } else {
            create(entity);
        }
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    private void edit(T entity) {
        entityManager.merge(entity);
    }

    public void removeAll() {
        List<T> entities = createAllEntities();
        for (T entity : entities) {
            entityManager.remove(entity);
        }
    }

    public void remove(T entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    public T find(Object id) {
        return entityManager.find(entityClass, id);
    }

    public T find(Object id, LockModeType lockMode) {
        return entityManager.find(entityClass, id, lockMode);
    }

    public List<T> createAllEntities() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return entityManager.createQuery(cq).getResultList();
    }

    public long count() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(entityManager.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = entityManager.createQuery(cq);
        return (Long) q.getSingleResult();
    }
}
