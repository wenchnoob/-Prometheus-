package com.wench.prometheus.controllers.rest;

import com.wench.prometheus.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
public class LoginEndpoint {

    private LoginService loginService;

    @Autowired
    public LoginEndpoint(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(path="/login/authenticate")
    public RedirectView authenticate(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        request.getSession().setAttribute("loggedInUser", loginService.login(username, password));
        return new RedirectView("/userHome");
    }
}
