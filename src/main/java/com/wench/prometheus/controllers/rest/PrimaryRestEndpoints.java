package com.wench.prometheus.controllers.rest;


import com.wench.prometheus.calculator.Expression;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

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
        return "{ \"status\": \"success\"}";
    }

    @PostMapping(path="/signUp/authenticate", produces="application/json")
    @ResponseBody
    public String signup(@RequestBody MultiValueMap<String, String> formParams) {
        return "{ \"status\": \"success\"}";
    }

}
