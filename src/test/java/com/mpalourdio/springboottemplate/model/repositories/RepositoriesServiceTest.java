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
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RepositoriesServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private TaskRepository taskRepository;

    private RepositoriesService repositoriesService;

    @BeforeEach
    void setUp() {
        repositoriesService = new RepositoriesService(
                peopleRepository, taskRepository, entityManager
        );
    }

    @Test
    void testEntityManagerIsMocked() {
        var expectedId = 666;

        var people = new People();
        people.setId(expectedId);

        Mockito.when(entityManager.getReference(People.class, 1)).thenReturn(people);
        assertEquals(expectedId, repositoriesService.useEntityManager().getId());
    }
}
