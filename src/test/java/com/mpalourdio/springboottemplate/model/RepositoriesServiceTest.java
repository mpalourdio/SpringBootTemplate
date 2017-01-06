/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;

@RunWith(MockitoJUnitRunner.class)
public class RepositoriesServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private RepositoriesService repositoriesService;

    @Test
    public void testEntityManagerIsMocked() {
        final int expectedId = 666;

        final People people = new People();
        people.setId(expectedId);

        Mockito.when(entityManager.getReference(People.class, 1)).thenReturn(people);
        Assert.assertEquals(expectedId, repositoriesService.useEntityManager().getId());
    }
}
