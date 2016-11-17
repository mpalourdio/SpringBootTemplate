package com.mpalourdio.hello.controllers;

import com.mpalourdio.hello.model.People;
import com.mpalourdio.hello.model.RepositoriesService;
import com.mpalourdio.hello.model.Task;
import com.mpalourdio.hello.model.TaskRepository;
import com.mpalourdio.hello.service.UselessBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
class HomeController {

    private final String myProperty;
    private final RepositoriesService lazyService;
    private final UselessBean uselessBean;
    private final TaskRepository taskRepository;

    public HomeController(
            final TaskRepository taskRepository,
            final RepositoriesService lazyService,
            final UselessBean uselessBean,
            @Value("${property.whatever}") final String myProperty
    ) {
        this.taskRepository = taskRepository;
        this.lazyService = lazyService;
        this.uselessBean = uselessBean;
        this.myProperty = myProperty;
    }

    @GetMapping("/")
    String indexAction(final Model model) {
        final List task = taskRepository.findByTaskStatus("ACTIVE");
        final Task activity = taskRepository.findOne(1);
        model.addAttribute("iwantthisinmyview", uselessBean.getTestPro());
        model.addAttribute("iwantthisinmyviewfromhibernate", activity.getTaskName());

        return "home/index";
    }

    @GetMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<People> fetchLazyCollections() {
        //this will throw an exception because of lazy collection initialization
        return lazyService.getAllPeople();
    }

    @GetMapping("/other")
    String otherAction(final Model model) {
        uselessBean.setTestPro("imsetinthecontrolleronthefly");
        final List task = taskRepository.findByTaskStatus("ACTIVE");
        final Task activity = taskRepository.findOne(1);
        model.addAttribute("iwantthisinmyview", uselessBean.getTestPro());
        model.addAttribute("iwantthisinmyviewfromhibernate", activity.getTaskName());
        model.addAttribute("iwantthisinmyviewfromproperties", myProperty);

        return "home/index";
    }

    @GetMapping("/custorepo")
    String customRepoAction(final Model model) {
        //use a custom method repository
        final List<Task> mediumTasks = taskRepository.customFindByPriority("MEDIUM");
        //print only those who have 'Implementation' as task_name
        mediumTasks.
                stream().
                filter(s -> s.getTaskName().equals("Implementation")).
                forEach(System.out::println);

        return "home/index";
    }
}
