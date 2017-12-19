/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.model.entities.Task;
import com.mpalourdio.springboottemplate.model.repositories.TaskRepository;
import com.mpalourdio.springboottemplate.service.ServiceWithProperties;
import com.mpalourdio.springboottemplate.service.UselessBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

    private static final String IWANTTHISINMYVIEW = "iwantthisinmyview";
    private static final String IWANTTHISINMYVIEWFROMHIBERNATE = "iwantthisinmyviewfromhibernate";

    private static final String HTTPS_JSONPLACEHOLDER_TYPICODE_COM_POSTS_1 = "https://jsonplaceholder.typicode.com/posts/1";
    private static final String HOME_INDEX = "home/index";
    private static final String NEW_BODY = "new body";

    private final ServiceWithProperties serviceWithProperties;
    private final String myProperty;
    private final UselessBean uselessBean;
    private final TaskRepository taskRepository;

    public HomeController(
            TaskRepository taskRepository,
            UselessBean uselessBean,
            ServiceWithProperties serviceWithProperties,
            @Value("${property.whatever}") String myProperty
    ) {
        this.taskRepository = taskRepository;
        this.uselessBean = uselessBean;
        this.serviceWithProperties = serviceWithProperties;
        this.myProperty = myProperty;
    }

    @GetMapping("/")
    public String indexAction(Model model) {
        List task = taskRepository.findByTaskStatus(TASK_STATUS_ACTIVE);
        Task activity = taskRepository.findById(1).orElse(null);
        model.addAttribute(IWANTTHISINMYVIEW, uselessBean.getTestPro());
        model.addAttribute(IWANTTHISINMYVIEWFROMHIBERNATE, activity.getTaskName());

        return HOME_INDEX;
    }

    @GetMapping("/other")
    public String otherAction(Model model) {
        uselessBean.setTestPro("imsetinthecontrolleronthefly");
        List task = taskRepository.findByTaskStatus(TASK_STATUS_ACTIVE);
        Task activity = taskRepository.findById(1).orElse(null);
        model.addAttribute(IWANTTHISINMYVIEW, uselessBean.getTestPro());
        model.addAttribute(IWANTTHISINMYVIEWFROMHIBERNATE, activity.getTaskName());
        model.addAttribute("iwantthisinmyviewfromproperties", myProperty);

        return HOME_INDEX;
    }

    @GetMapping("/custorepo")
    public String customRepoAction(Model model) {
        //use a custom method repository
        List<Task> mediumTasks = taskRepository.customFindByPriority(TASK_PRIORITY_MEDIUM);
        //print only those who have 'Implementation' as task_name
        mediumTasks.stream()
                .filter(s -> s.getTaskName().equals("Implementation"))
                .forEach(System.out::println);

        return HOME_INDEX;
    }

    @GetMapping("/invalidpath")
    @ResponseBody
    public String testInvalidPath() {
        List<Task> tasks = taskRepository.testInvalidPath();
        if (tasks.size() > 0) {
            return tasks.get(0).getTaskName();
        }
        return "No matching results. Is the DB provisionned ?";
    }

    @GetMapping("/valueinconstructor")
    @ResponseBody
    public String valueinConstructor() {
        return serviceWithProperties.getValueFromConfig();
    }

    @GetMapping("patchwithrestemplate")
    @ResponseBody
    public JsonPlaceHolder testPatchWithRestTemplate() {
        RestTemplate restTemplateforGet = new RestTemplate();
        JsonPlaceHolder jsonPlaceHolder = restTemplateforGet.getForObject(
                HTTPS_JSONPLACEHOLDER_TYPICODE_COM_POSTS_1,
                JsonPlaceHolder.class
        );

        jsonPlaceHolder.body = NEW_BODY;

        RestTemplate restTemplateForPatch = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        return restTemplateForPatch.patchForObject(
                HTTPS_JSONPLACEHOLDER_TYPICODE_COM_POSTS_1,
                jsonPlaceHolder,
                JsonPlaceHolder.class
        );
    }

    @GetMapping("patchwithrestemplatebuilder")
    @ResponseBody
    public JsonPlaceHolder testPatchWithRestTemplateBuilder() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        JsonPlaceHolder jsonPlaceHolder = restTemplate.getForObject(
                HTTPS_JSONPLACEHOLDER_TYPICODE_COM_POSTS_1,
                JsonPlaceHolder.class
        );

        jsonPlaceHolder.body = NEW_BODY;
        return restTemplate.patchForObject(
                HTTPS_JSONPLACEHOLDER_TYPICODE_COM_POSTS_1,
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
