package com.mpalourdio.hello.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer>, CustomRepository<Task> {

    List<Task> findByTaskArchived(int taskArchivedFalse);

    List<Task> findByTaskStatus(String taskStatus);

    @Override
    List<Task> findAll();
}

