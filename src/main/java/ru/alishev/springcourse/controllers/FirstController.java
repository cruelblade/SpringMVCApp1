package ru.alishev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
            public String helloPage(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "surname", required = false) String surname,
                                    Model model) {

        // System.out.println("Hello, " + name + " " + surname);
        model.addAttribute("message", "Hello, " + name + " " + surname);

        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodBye";
    }

    @GetMapping("/calculator")
    public String firstCalculator(@RequestParam(value = "a", required = false) Integer a,
                            @RequestParam(value = "b", required = false) Integer b,
                            @RequestParam(value = "action", required = false) String action,
                            Model model) {

        int result = 0;
        if (action != null) {
            switch (action) {
                case "multiplication" -> result = a * b;
                case "addition" -> result = a + b;
                case "subtraction" -> result = a - b;
                case "division" -> result = (int) a / b;
            }
        }

        model.addAttribute("result", result);

        return "first/calculator";
    }
}
