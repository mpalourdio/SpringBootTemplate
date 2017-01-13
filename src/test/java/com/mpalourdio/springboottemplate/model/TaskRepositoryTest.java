package com.mpalourdio.springboottemplate.model;

import com.mpalourdio.springboottemplate.AbstractTestRunner;
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
    private People people;

    @Before
    public void setUp() {
        task = new Task();
        task.setTaskArchived(true);
        task.setTaskName("name");
        task.setTaskDescription("description");
        task.setTaskPriority("LOW");
        task.setTaskStatus("ACTIVE");

        people = new People();
        people.setName("john");
        people.setTask(task);
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

    @Test
    public void testResultsAreDummyObjects() {
        entityManager.persist(task);
        entityManager.persist(people);
        final List<Dummy> dummyList = taskRepository.hydrateDummyObject();

        Assert.assertEquals(1, dummyList.size());
        Assert.assertEquals(true, (dummyList.get(0)) instanceof Dummy);
    }
}
