package com.mpalourdio.hello.model;

import java.util.List;

interface CustomRepository<T extends Task> {
    public List<T> customFindByPriority(String priority);
}
