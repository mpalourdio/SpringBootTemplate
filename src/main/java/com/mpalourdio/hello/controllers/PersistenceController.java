/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.hello.controllers;

import com.mpalourdio.hello.model.People;
import com.mpalourdio.hello.model.RepositoriesService;
import com.mpalourdio.hello.model.TaskRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersistenceController {

    private final TaskRepository taskRepository;
    private final RepositoriesService repositoriesService;

    public PersistenceController(final TaskRepository taskRepository, final RepositoriesService repositoriesService) {
        this.taskRepository = taskRepository;
        this.repositoriesService = repositoriesService;
    }

    @GetMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<People> fetchLazyCollections() {
        //this will throw an exception because of lazy collection initialization
        return repositoriesService.getAllPeople();
    }
}
