/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.model;

import com.mpalourdio.springboottemplate.model.entities.People;
import com.mpalourdio.springboottemplate.model.entities.Task;
import com.mpalourdio.springboottemplate.model.repositories.PeopleRepository;
import com.mpalourdio.springboottemplate.model.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class RepositoriesService {

    private final EntityManager entityManager;
    private final PeopleRepository peopleRepository;
    private final TaskRepository taskRepository;

    public RepositoriesService(
            PeopleRepository peopleRepository,
            TaskRepository taskRepository,
            EntityManager entityManager) {
        this.peopleRepository = peopleRepository;
        this.taskRepository = taskRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public List<People> getAllPeople() {
        return peopleRepository.findAll();
    }

    @Transactional
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public People useEntityManager() {
        return entityManager.getReference(People.class, 1);
    }
}
