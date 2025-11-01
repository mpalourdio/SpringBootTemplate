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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.transaction.TestTransaction;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(taskList).isEmpty();
    }

    @Test
    void testAndPlayWithTheFakeentityManager() {
        var persistedTask = entityManager.persistFlushFind(task);
        assertThat(persistedTask.getTaskDescription()).isEqualTo("description");
    }

    @Test
    void testResultsAreDummyObjects() {
        entityManager.persist(task);
        entityManager.persist(people);
        var dummyList = taskRepository.hydrateDummyObject();

        assertThat(dummyList).hasSize(1);
        assertThat(dummyList.getFirst()).isNotNull();
    }

    @Test
    void testAnnotatedQuery() {
        entityManager.persist(task);
        entityManager.persist(people);
        var allTasksByArchivedValue = taskRepository.getAllTasksByArchivedValue(true);

        assertThat(allTasksByArchivedValue).hasSize(1);
    }

    @Test
    void testManuallyEndstheTransaction() {
        entityManager.persist(task);
        entityManager.persist(people);
        TestTransaction.end();

        var allTasksByArchichedValue = taskRepository.getAllTasksByArchivedValue(true);
        assertThat(TestTransaction.isActive()).isFalse();
        assertThat(allTasksByArchichedValue).isEmpty();
    }
}
