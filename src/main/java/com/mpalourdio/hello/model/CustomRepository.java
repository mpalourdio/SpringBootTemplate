package com.mpalourdio.hello.model;

import java.util.List;

interface CustomRepository<T> {

    List<T> customFindByPriority(String priority);
}
