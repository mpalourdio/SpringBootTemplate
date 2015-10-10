package com.mpalourdio.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeController {
    @Value("${property.whatever}")
    private String myProperty;

    @RequestMapping("/")
    String indexAction(Model model) {

        model.addAttribute("iwantthisinmyview", this.myProperty);
        return "home/index";
    }
}
