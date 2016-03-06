package com.mpalourdio.hello.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public List<Task> cumstomFindByPriority(String priority) {
        return this.
                entityManager.
                createQuery("select e from Task e where e.taskPriority = '" + priority + "'").
                getResultList();
    }
}
