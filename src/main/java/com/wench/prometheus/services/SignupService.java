package com.wench.prometheus.services;

import com.wench.prometheus.data.user.User;
import com.wench.prometheus.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    private UserRepository userRepository;

    public SignupService() {}

    @Autowired
    public SignupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(String first_name, String last_name, String display_name,
                       String username, String password, String email) {

        for (User user : userRepository.findAll()) {
            if (user.getUserName() == username) return null;
        }

        User user = new User(first_name, last_name, display_name, username, password, email);
        userRepository.save(user);

        return user;
    }

}
