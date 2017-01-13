package com.mpalourdio.springboottemplate.model;

import java.util.List;

interface CustomRepository<T> {

    List<T> customFindByPriority(String priority);

    List<Dummy> hydrateDummyObject();
}
