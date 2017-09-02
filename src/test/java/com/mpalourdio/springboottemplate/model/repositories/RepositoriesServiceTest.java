/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.model.repositories;

import com.mpalourdio.springboottemplate.model.RepositoriesService;
import com.mpalourdio.springboottemplate.model.entities.People;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class RepositoriesServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private TaskRepository taskRepository;

    private RepositoriesService repositoriesService;

    @Before
    public void setUp() {
        repositoriesService = new RepositoriesService(
                peopleRepository, taskRepository, entityManager
        );
    }

    @Test
    public void testEntityManagerIsMocked() {
        int expectedId = 666;

        People people = new People();
        people.setId(expectedId);

        Mockito.when(entityManager.getReference(People.class, 1)).thenReturn(people);
        Assert.assertEquals(expectedId, repositoriesService.useEntityManager().getId());
    }
}
