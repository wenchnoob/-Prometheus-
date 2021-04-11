package com.wench.prometheus.controllers.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

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
    public String calculator(HttpServletRequest request) {

        return "calculator";
    }

    @GetMapping(path="/code", produces="text/html")
    public String code() {
        return "ProgLang";
    }

    @GetMapping(path="/userHome", produces = "text/html")
    public String userHome(HttpServletRequest request, Model model) {
        model.addAttribute("user", request.getSession().getAttribute("loggedInUser"));
        return "userHome";
    }
}
