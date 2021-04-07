package com.wench.prometheus.controllers.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrimaryEndpoints {

    @GetMapping(path="/index", produces="text/html")
    public String index() {
        return "index";
    }

    @GetMapping(path="/login", produces="text/html")
    public String login() {
        return "login";
    }

    @GetMapping(path="/signUp", produces="text/html")
    public String signup() {
        return "signUp";
    }

    @GetMapping(path="/calculator", produces="text/html")
    public String calculator() {
        return "calculator";
    }

    @GetMapping(path="/code", produces="text/html")
    public String code() {
        return "ProgLang";
    }
}
