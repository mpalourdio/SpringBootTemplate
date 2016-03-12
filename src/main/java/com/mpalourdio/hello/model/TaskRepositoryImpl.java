package com.mpalourdio.hello.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * This implementation MUST have the 'impl'
 * suffix in order to be discovered !
 */

public class TaskRepositoryImpl implements CustomRepository<Task> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Task> customFindByPriority(String priority) {
        return this.
                entityManager.
                createQuery("select e from Task e where e.taskPriority = '" + priority + "'").
                getResultList();
    }

    static <T> void fromArrayToCollection(T[] a, Collection<T> c) {
        for (T o : a) {
            c.add(o); // compile-time error
        }
    }
}
