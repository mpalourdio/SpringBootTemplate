package com.mpalourdio.hello.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByTaskArchived(int taskArchivedFalse);

    List<Task> findByTaskStatus(String taskStatus);
}

