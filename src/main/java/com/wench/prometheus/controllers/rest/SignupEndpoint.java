package com.wench.prometheus.controllers.rest;

import com.wench.prometheus.data.user.User;
import com.wench.prometheus.data.user.UserRepository;
import com.wench.prometheus.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SignupEndpoint {

    private SignupService signupService;

    @Autowired
    public SignupEndpoint(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping(path="/signUp/authenticate")
    public RedirectView signup(@RequestParam String first_name, @RequestParam String last_name, @RequestParam String display_name,
                               @RequestParam(required = false) String email, @RequestParam String username,
                               @RequestParam String password, @RequestParam String confirmPassword, HttpServletRequest request) {
        if(!password.equals(confirmPassword)) return new RedirectView("/signUp?error=1");

        User signedUp = signupService.signUp(first_name, last_name, display_name, username, password, email);
        request.getSession().setAttribute("loggedInUser", signedUp);

        return new RedirectView("/userHome");
    }
}
