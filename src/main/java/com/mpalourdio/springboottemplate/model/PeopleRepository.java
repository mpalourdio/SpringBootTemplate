package com.mpalourdio.springboottemplate.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends CrudRepository<People, Integer> {

    List<People> findByTask(Task task);

    List<People> findByName(String name);

    @Override
    List<People> findAll();
}

