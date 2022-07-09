package com.ddoong2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message", "world");
        return "hello";
    }

    @GetMapping("/hi")
    public String hi(Model model) {
        model.addAttribute("message", "hi");
        return "hi";
    }
}
