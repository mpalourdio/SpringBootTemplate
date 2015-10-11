package com.mpalourdio.hello.controllers;

import com.mpalourdio.hello.model.Task;
import com.mpalourdio.hello.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
class HomeController {
    @Value("${property.whatever}")
    private String myProperty;

    private TaskRepository taskRepository;

    @Autowired
    public HomeController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @RequestMapping("/")
    String indexAction(Model model) {
        List task = taskRepository.findByTaskStatus("ACTIVE");
        Task activity = taskRepository.findOne(1);
        model.addAttribute("iwantthisinmyview", this.myProperty);
        model.addAttribute("iwantthisinmyviewfromhibernate", activity.getTaskName());
        return "home/index";
    }
}
