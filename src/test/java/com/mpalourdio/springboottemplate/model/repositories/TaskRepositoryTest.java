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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.transaction.TestTransaction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest extends AbstractTestRunner {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        initializeData();
    }

    @Test
    void testTableIsEmpty() {
        var taskList = taskRepository.findAll();
        assertEquals(0, taskList.size());
    }

    @Test
    void testAndPlayWithTheFakeentityManager() {
        var persistedTask = entityManager.persistFlushFind(task);
        assertEquals("description", persistedTask.getTaskDescription());
    }

    @Test
    void testResultsAreDummyObjects() {
        entityManager.persist(task);
        entityManager.persist(people);
        var dummyList = taskRepository.hydrateDummyObject();

        assertEquals(1, dummyList.size());
        assertNotNull(dummyList.get(0));
    }

    @Test
    void testAnnotatedQuery() {
        entityManager.persist(task);
        entityManager.persist(people);
        var allTasksByArchivedValue = taskRepository.getAllTasksByArchivedValue(true);

        assertEquals(1, allTasksByArchivedValue.size());
    }

    @Test
    void testManuallyEndstheTransaction() {
        entityManager.persist(task);
        entityManager.persist(people);
        TestTransaction.end();

        var allTasksByArchichedValue = taskRepository.getAllTasksByArchivedValue(true);
        assertFalse(TestTransaction.isActive());
        assertEquals(0, allTasksByArchichedValue.size());
    }
}
