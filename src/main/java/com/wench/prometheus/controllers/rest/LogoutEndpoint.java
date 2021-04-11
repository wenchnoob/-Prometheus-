package com.wench.prometheus.controllers.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutEndpoint {

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new RedirectView("/index");
    }

}
