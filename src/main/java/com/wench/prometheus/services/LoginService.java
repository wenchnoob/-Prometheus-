package com.wench.prometheus.services;

import com.wench.prometheus.data.user.User;
import com.wench.prometheus.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private UserRepository userRepository;

    public LoginService() { }

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User user = userRepository.findById(username).orElse(null);
        if (user != null)
            if (user.getPassWord().equals(password)) return user;
        return null;
    }
}
