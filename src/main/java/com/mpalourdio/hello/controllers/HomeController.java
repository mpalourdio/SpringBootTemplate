package com.mpalourdio.hello.controllers;

import com.mpalourdio.hello.model.Task;
import com.mpalourdio.hello.model.TaskRepository;
import com.mpalourdio.hello.service.UselessBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
class HomeController {

    private final String myProperty;
    private final UselessBean uselessBean;
    private final TaskRepository taskRepository;

    public HomeController(
            final TaskRepository taskRepository,
            final UselessBean uselessBean,
            @Value("${property.whatever}") final String myProperty
    ) {
        this.taskRepository = taskRepository;
        this.uselessBean = uselessBean;
        this.myProperty = myProperty;
    }

    @GetMapping("/")
    String indexAction(final Model model) {
        final List task = taskRepository.findByTaskStatus("ACTIVE");
        final Task activity = taskRepository.findOne(1);
        model.addAttribute("iwantthisinmyview", this.uselessBean.getTestPro());
        model.addAttribute("iwantthisinmyviewfromhibernate", activity.getTaskName());

        return "home/index";
    }

    @GetMapping("/other")
    String otherAction(final Model model) {
        this.uselessBean.setTestPro("imsetinthecontrolleronthefly");
        final List task = this.taskRepository.findByTaskStatus("ACTIVE");
        final Task activity = this.taskRepository.findOne(1);
        model.addAttribute("iwantthisinmyview", this.uselessBean.getTestPro());
        model.addAttribute("iwantthisinmyviewfromhibernate", activity.getTaskName());
        model.addAttribute("iwantthisinmyviewfromproperties", this.myProperty);

        return "home/index";
    }

    @GetMapping("/custorepo")
    String customRepoAction(final Model model) {
        //use a custom method repository
        final List<Task> mediumTasks = this.taskRepository.customFindByPriority("MEDIUM");
        //print only those who have 'Implementation' as task_name
        mediumTasks.
                stream().
                filter(s -> s.getTaskName().equals("Implementation")).
                forEach(System.out::println);

        return "home/index";
    }
}
