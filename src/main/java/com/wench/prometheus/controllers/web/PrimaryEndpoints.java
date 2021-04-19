package com.wench.prometheus.controllers.web;


import com.wench.prometheus.controllers.rest.ExpressionResource;
import com.wench.prometheus.data.user.User;
import graphql.ExecutionResult;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Controller
public class PrimaryEndpoints implements ApplicationContextAware {

    private ApplicationContext applicationContext;

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

    @GetMapping(path="/viewCalculations", produces="text/html")
    public String viewCalculations(HttpServletRequest request, Model model) {
        String username = ((User)request.getSession().getAttribute("loggedInUser")).getUserName();
        ExpressionResource expressionResource = applicationContext.getBean(ExpressionResource.class);
        ExecutionResult response = (ExecutionResult)expressionResource.getUserExpressions(("{\n" +
                " calculationsByUsername(username:  \"wenchy\") {\n" +
                "        id\n" +
                "        val\n" +
                "        solution\n" +
                "    }\n" +
                " }").formatted(username)).getBody();
        model.addAttribute("expressions",((LinkedHashMap)response.getData()).get("calculationsByUsername"));
        return "calculationsView";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
