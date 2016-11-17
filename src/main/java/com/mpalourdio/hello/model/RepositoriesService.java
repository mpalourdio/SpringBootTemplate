/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.hello.model;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class RepositoriesService {

    @PersistenceContext
    private EntityManager entityManager;

    private final PeopleRepository peopleRepository;
    private final TaskRepository taskRepository;

    public RepositoriesService(final PeopleRepository peopleRepository, final TaskRepository taskRepository) {
        this.peopleRepository = peopleRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public List<People> getAllPeople() {
        return peopleRepository.findAll();
    }

    @Transactional
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
