/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.mediatype.MediaType;
import com.mpalourdio.springboottemplate.model.Dummy;
import com.mpalourdio.springboottemplate.model.RepositoriesService;
import com.mpalourdio.springboottemplate.model.entities.People;
import com.mpalourdio.springboottemplate.model.entities.Task;
import com.mpalourdio.springboottemplate.model.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PersistenceController {

    private final TaskRepository taskRepository;
    private final RepositoriesService repositoriesService;

    public PersistenceController(TaskRepository taskRepository, RepositoriesService repositoriesService) {
        this.taskRepository = taskRepository;
        this.repositoriesService = repositoriesService;
    }

    @GetMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<People> fetchLazyCollections() {
        //this will throw an exception because of lazy collection initialization
        return repositoriesService.getAllPeople();
    }

    @GetMapping(value = "/testinsert", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task testInsert() {
        var aspecificstatusfordate = "ASPECIFICSTATUSFORDATE";

        var task = new Task();
        task.setTaskName("name");
        task.setTaskDescription("desc");
        task.setTaskStatus(aspecificstatusfordate);
        task.setTaskPriority("LOW");

        taskRepository.save(task);

        var allTasksByStatus = taskRepository.findByTaskStatus(aspecificstatusfordate);

        allTasksByStatus.forEach(t -> log.debug(t.getStartDate().toString()));

        return task;
    }

    @GetMapping(value = "/hydrate", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dummy> hydrate() {
        return taskRepository.hydrateDummyObject();
    }

    @GetMapping(value = "/annotated", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int annotated() {
        var allTasksByArchichedValue = taskRepository.getAllTasksByArchivedValue(false);

        return allTasksByArchichedValue.size();
    }
}
