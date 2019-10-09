package at.htl.krankenhaus.business;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * To create a data access object:
 * 1) Extend from this class
 * 2) Add @ApplicationScoped
 * 3) Call super(<CHILD>.class, "<CHILD>.findAll"); in the constructor
 */
public abstract class AbstractDao<T> {

    @PersistenceContext
    EntityManager em;

    final Class<T> clazz;
    final String findAllQueryName;

    public AbstractDao(Class<T> clazz, String findAllQueryName) {
        this.clazz = clazz;
        this.findAllQueryName = findAllQueryName;
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public void insert(T entity) {
        em.persist(entity);
    }

    public T find(Long id) {
        return em.find(clazz, id);
    }

    public List<T> findAll() {
        List<T> all = em.createNamedQuery(findAllQueryName, clazz).getResultList();
        return all;
    }

    public void delete(Long id) {
        delete(find(id));
    }

    public void delete(T entity) {
        em.remove(entity);
    }

    public void flushAndRefresh(T entity) {
        em.flush(); // Force save to database, can throw a PersistenceException
        em.refresh(entity); // Refresh entity from database
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
