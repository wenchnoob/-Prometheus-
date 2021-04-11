package com.wench.prometheus.controllers.rest;

import com.wench.prometheus.data.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileEndpoint {

    @GetMapping(value ="/viewProfile")
    public ModelAndView get(HttpServletRequest request, ModelMap model) {
        Object loggedInUser = request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getClass() != User.class) return new ModelAndView("index");
        model.addAttribute("user", loggedInUser);
        return new ModelAndView("profile", model);
    }

    @GetMapping(value = "/profile")
    public String view() {
        return "profile";
    }
}
