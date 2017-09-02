/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.model.repositories;

import com.mpalourdio.springboottemplate.AbstractTestRunner;
import com.mpalourdio.springboottemplate.model.Dummy;
import com.mpalourdio.springboottemplate.model.entities.Task;
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

    @Before
    public void setUp() {
        initializeData();
    }

    @Test
    public void testTableIsEmpty() {
        List<Task> taskList = taskRepository.findAll();
        Assert.assertEquals(0, taskList.size());
    }

    @Test
    public void testAndPlayWithTheFakeentityManager() {
        Task persistedTask = entityManager.persistFlushFind(task);
        Assert.assertEquals(persistedTask.getTaskDescription(), "description");
    }

    @Test
    public void testResultsAreDummyObjects() {
        entityManager.persist(task);
        entityManager.persist(people);
        List<Dummy> dummyList = taskRepository.hydrateDummyObject();

        Assert.assertEquals(1, dummyList.size());
        Assert.assertEquals(true, (dummyList.get(0)) instanceof Dummy);
    }

    @Test
    public void testAnnotatedQuery() {
        entityManager.persist(task);
        entityManager.persist(people);
        List<Task> allTasksByArchichedValue = taskRepository.getAllTasksByArchichedValue(true);

        Assert.assertEquals(1, allTasksByArchichedValue.size());
    }
}
