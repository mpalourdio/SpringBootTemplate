package com.mpalourdio.hello.controllers;

import com.mpalourdio.hello.model.Task;
import com.mpalourdio.hello.model.TaskRepository;
import com.mpalourdio.hello.service.UselessBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
class HomeController {

    private String myProperty;
    private UselessBean uselessBean;
    private TaskRepository taskRepository;

    @Autowired
    public HomeController(
            TaskRepository taskRepository,
            UselessBean uselessBean,
            @Value("${property.whatever}") String myProperty
    ) {
        this.taskRepository = taskRepository;
        this.uselessBean = uselessBean;
        this.myProperty = myProperty;
    }

    @RequestMapping("/")
    String indexAction(Model model) {
        List task = taskRepository.findByTaskStatus("ACTIVE");
        Task activity = taskRepository.findOne(1);
        model.addAttribute("iwantthisinmyview", uselessBean.getTestPro());
        model.addAttribute("iwantthisinmyviewfromhibernate", activity.getTaskName());
        return "home/index";
    }

    @RequestMapping("/other")
    String otherAction(Model model) {
        uselessBean.setTestPro("imsetinthecontrolleronthefly");
        List task = taskRepository.findByTaskStatus("ACTIVE");
        Task activity = taskRepository.findOne(1);
        model.addAttribute("iwantthisinmyview", uselessBean.getTestPro());
        model.addAttribute("iwantthisinmyviewfromhibernate", activity.getTaskName());
        model.addAttribute("iwantthisinmyviewfromproperties", myProperty);
        return "home/index";
    }

    @RequestMapping("custorepo")
    String customRepoAction(Model model) {
        //use a custom method repository
        List<Task> mediumTasks = taskRepository.customFindByPriority("MEDIUM");
        //print only those who have 'Implementation' as task_name
        mediumTasks.
                stream().
                filter(s -> s.getTaskName().equals("Implementation")).
                forEach(System.out::println);

        return "home/index";
    }

}
