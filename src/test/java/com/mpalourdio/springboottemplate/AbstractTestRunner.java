/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate;

import app.config.PropertyPlaceholderConfig;
import com.mpalourdio.springboottemplate.model.People;
import com.mpalourdio.springboottemplate.model.Task;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@Import({PropertyPlaceholderConfig.class})
public abstract class AbstractTestRunner {

    protected Task task;
    protected People people;

    public void initializeData() {
        task = new Task();
        task.setTaskArchived(true);
        task.setTaskName("name");
        task.setTaskDescription("description");
        task.setTaskPriority("LOW");
        task.setTaskStatus("ACTIVE");

        people = new People();
        people.setName("john");
        people.setTask(task);
    }
}
