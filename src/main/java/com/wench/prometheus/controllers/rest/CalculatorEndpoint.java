package com.wench.prometheus.controllers.rest;

import com.wench.prometheus.data.expression.Expression;
import com.wench.prometheus.data.expression.ExpressionRepository;
import com.wench.prometheus.data.user.User;
import com.wench.prometheus.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
public class CalculatorEndpoint {

    private UserRepository userRepository;
    private ExpressionRepository expressionRepository;

    @Autowired
    public CalculatorEndpoint(UserRepository userRepository, ExpressionRepository expressionRepository) {
        this.userRepository = userRepository;
        this.expressionRepository = expressionRepository;
    }

    @GetMapping(path="/calculator/solve", produces="application/json")
    public String solve(@RequestParam String expression_input, HttpServletRequest request) {
        Object loggedInUser = request.getSession().getAttribute("loggedInUser");
        Expression expression;
        if (loggedInUser == null || loggedInUser.getClass() != User.class)
            expression = new Expression(expression_input.replaceAll("plusSign", "+"));
        else expression = new Expression(expression_input.replaceAll("plusSign", "+"), ((User) loggedInUser).getUserName());
        expressionRepository.save(expression);
        return "{ \"val\": \"" + expression.solution() + "\"}";
    }
}
