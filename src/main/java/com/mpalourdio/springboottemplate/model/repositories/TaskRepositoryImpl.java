/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.model.repositories;

import com.mpalourdio.springboottemplate.model.Dummy;
import com.mpalourdio.springboottemplate.model.entities.Task;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * This implementation MUST have the 'impl'
 * suffix in order to be discovered !
 */

public class TaskRepositoryImpl implements CustomRepository<Task> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Task> customFindByPriority(String priority) {
        return entityManager
                .createQuery("select e from Task e where e.taskPriority = :taskPriority", Task.class)
                .setParameter("taskPriority", priority)
                .getResultList();
    }

    @Override
    public List<Dummy> hydrateDummyObject() {
        return entityManager
                .createQuery("select NEW com.mpalourdio.springboottemplate.model.Dummy(p.name, e.taskDescription) "
                        + "from Task e JOIN e.people p", Dummy.class)
                .getResultList();
    }

    @Override
    public List<Task> testInvalidPath() {
        // you need to JOIN p.task here to avoid 'invalid path' QuerySyntaxException
        // this will not work :
        //    -> select p.task from People p where t.taskArchived = true
        return entityManager
                .createQuery("select t from People p join p.task t where t.taskArchived = true", Task.class)
                .getResultList();
    }
}
