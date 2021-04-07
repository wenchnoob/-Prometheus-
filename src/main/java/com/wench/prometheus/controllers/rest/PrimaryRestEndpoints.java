package com.wench.prometheus.controllers.rest;


import com.wench.prometheus.calculator.Expression;
import com.wench.prometheus.data.DataDriver;
import com.wench.prometheus.user.User;
import com.wench.prometheus.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IContext;

import javax.activation.DataHandler;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class PrimaryRestEndpoints {

    @GetMapping(path="/calculator/solve", produces="application/json")
    @ResponseBody
    public String solve(@RequestParam String expression) {
        return "{ \"val\": \"" +new Expression(expression.replaceAll("plusSign", "+")).solution() + "\"}";
    }

    @PostMapping(path="/login/authenticate", produces="application/json")
    @ResponseBody
    public String authenticate(@RequestBody MultiValueMap<String, String> formParams) {
        String username = formParams.getFirst("username");
        String password = formParams.getFirst("password");

        Connection conn = null;
        try {
           conn = new DataDriver().connect("", null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (conn != null) {
            try {
                conn.createStatement().execute("INSERT INTO users(username, password) VALUES ('" + username + "','" + password + "')");
            } catch (SQLException throwables) {
                System.out.println("An error occurred while executing your statement.");
                throwables.printStackTrace();
            }
        } else {
            System.out.println("Connection has failed.");
        }

        User user = new User();

        return "{ \"status\": \"success\"}";
    }

    // Private instance variable to allow for the saving of signup users.
    @Autowired
    private UserRepository userRepository;

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
