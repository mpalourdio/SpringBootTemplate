package com.mpalourdio.springboottemplate.model;

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
    public List<Task> customFindByPriority(final String priority) {
        return entityManager
                .createQuery("select e from Task e where e.taskPriority = :taskPriority", Task.class)
                .setParameter("taskPriority", priority)
                .getResultList();
    }

    @Override
    public List<Dummy> hydrateDummyObject() {
        return entityManager
                .createQuery("select NEW com.mpalourdio.springboottemplate.model.Dummy(p.name, e.taskDescription) from Task e JOIN e.people p", Dummy.class)
                .getResultList();
    }
}
