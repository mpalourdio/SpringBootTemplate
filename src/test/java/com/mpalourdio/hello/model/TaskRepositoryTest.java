package com.mpalourdio.hello.model;

import com.mpalourdio.hello.AbstractTestRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
public class TaskRepositoryTest extends AbstractTestRunner {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    private Task task;

    @Before
    public void setUp() {
        task = new Task();
        task.setTaskArchived(1);
        task.setTaskName("name");
        task.setTaskDescription("description");
        task.setTaskPriority("LOW");
        task.setTaskStatus("ACTIVE");
    }

    @Test
    public void testTableIsEmpty() {
        final List<Task> taskList = taskRepository.findAll();
        Assert.assertEquals(0, taskList.size());
    }

    @Test
    public void testAndPlayWithTheFakeentityManager() {
        final Task persistedTask = entityManager.persistFlushFind(task);
        Assert.assertEquals(persistedTask.getTaskDescription(), "description");
    }
}
