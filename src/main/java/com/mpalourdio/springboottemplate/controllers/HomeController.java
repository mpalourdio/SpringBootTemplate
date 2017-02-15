/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.model.Task;
import com.mpalourdio.springboottemplate.model.TaskRepository;
import com.mpalourdio.springboottemplate.service.ServiceWithProperties;
import com.mpalourdio.springboottemplate.service.UselessBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class HomeController {

    private static final String TASK_STATUS_ACTIVE = "ACTIVE";
    private static final String TASK_PRIORITY_MEDIUM = "MEDIUM";
    private final ServiceWithProperties serviceWithProperties;
    private final String myProperty;
    private final UselessBean uselessBean;
    private final TaskRepository taskRepository;

    public HomeController(
            final TaskRepository taskRepository,
            final UselessBean uselessBean,
            final ServiceWithProperties serviceWithProperties,
            @Value("${property.whatever}") final String myProperty
    ) {
        this.taskRepository = taskRepository;
        this.uselessBean = uselessBean;
        this.serviceWithProperties = serviceWithProperties;
        this.myProperty = myProperty;
    }

    @GetMapping("/")
    public String indexAction(final Model model) {
        final List task = taskRepository.findByTaskStatus(TASK_STATUS_ACTIVE);
        final Task activity = taskRepository.findOne(1);
        model.addAttribute("iwantthisinmyview", uselessBean.getTestPro());
        model.addAttribute("iwantthisinmyviewfromhibernate", activity.getTaskName());

        return "home/index";
    }

    @GetMapping("/other")
    public String otherAction(final Model model) {
        uselessBean.setTestPro("imsetinthecontrolleronthefly");
        final List task = taskRepository.findByTaskStatus(TASK_STATUS_ACTIVE);
        final Task activity = taskRepository.findOne(1);
        model.addAttribute("iwantthisinmyview", uselessBean.getTestPro());
        model.addAttribute("iwantthisinmyviewfromhibernate", activity.getTaskName());
        model.addAttribute("iwantthisinmyviewfromproperties", myProperty);

        return "home/index";
    }

    @GetMapping("/custorepo")
    public String customRepoAction(final Model model) {
        //use a custom method repository
        final List<Task> mediumTasks = taskRepository.customFindByPriority(TASK_PRIORITY_MEDIUM);
        //print only those who have 'Implementation' as task_name
        mediumTasks.
                stream().
                filter(s -> s.getTaskName().equals("Implementation")).
                forEach(System.out::println);

        return "home/index";
    }

    @GetMapping("/valueinconstructor")
    @ResponseBody
    public String valueinConstructor() {
        return serviceWithProperties.getValueFromConfig();
    }

    @GetMapping("patchwithrestemplate")
    @ResponseBody
    public JsonPlaceHolder testPatchWithRestTemplate() {
        final RestTemplate restTemplateforGet = new RestTemplate();
        final JsonPlaceHolder jsonPlaceHolder = restTemplateforGet.getForObject(
                "https://jsonplaceholder.typicode.com/posts/1",
                JsonPlaceHolder.class
        );

        jsonPlaceHolder.body = "new body";

        final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        return restTemplate.patchForObject(
                "https://jsonplaceholder.typicode.com/posts/1",
                jsonPlaceHolder,
                JsonPlaceHolder.class
        );
    }

    //needs to be static for jackson as it's a quick and dirty inner class
    static class JsonPlaceHolder {

        public Integer id;
        public String title;
        public String body;
        public Integer userId;
    }
}
