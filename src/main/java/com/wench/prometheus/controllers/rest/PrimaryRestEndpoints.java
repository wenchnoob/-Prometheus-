package com.wench.prometheus.controllers.rest;


import com.wench.prometheus.data.expression.Expression;
import com.wench.prometheus.data.expression.ExpressionRepository;
import com.wench.prometheus.data.user.User;
import com.wench.prometheus.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class PrimaryRestEndpoints {

    private UserRepository userRepository;
    private ExpressionRepository expressionRepository;

    @Autowired
    public PrimaryRestEndpoints(UserRepository userRepository, ExpressionRepository expressionRepository) {
        this.userRepository = userRepository;
        this.expressionRepository = expressionRepository;
    }

    @GetMapping(path="/calculator/solve", produces="application/json")
    @ResponseBody
    public String solve(@RequestParam String expression_input) {
        Expression expression = new Expression(expression_input.replaceAll("plusSign", "+"));

        expressionRepository.save(expression);

        return "{ \"val\": \"" + expression.solution() + "\"}";
    }

    @PostMapping(path="/login/authenticate", produces="application/json")
    @ResponseBody
    public String authenticate(@RequestBody MultiValueMap<String, String> formParams) {
        String username = formParams.getFirst("username");
        String password = formParams.getFirst("password");



        return "{ \"status\": \"success\"}";
    }

    // Private instance variable to allow for the saving of signup users.


    @PostMapping(path="/signUp/authenticate", produces="application/json")
    @ResponseBody
    public String signup(@RequestParam String first_name, @RequestParam String last_name, @RequestParam String display_name,
                         @RequestParam(required = false) String email, @RequestParam String username,
                         @RequestParam String password, @RequestParam String confirmPassword) {

        if(!password.equals(confirmPassword)) return "{\"status\": \"failed\"}";

        User user = new User(first_name, last_name, display_name, username, password, email);
        userRepository.save(user);
        return "{ \"status\": \"success\"}";
    }

}
