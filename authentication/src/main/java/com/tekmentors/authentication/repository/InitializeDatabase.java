package com.tekmentors.authentication.repository;

import com.tekmentors.authentication.model.Authority;
import com.tekmentors.authentication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitializeDatabase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void initData(){
        userRepository.deleteAll();
        User user = new User();
        user.setUsername("arun");
        user.setPassword(encoder.encode("arun"));
        user.setAuthorities(List.of(
                new Authority(null, "ADMIN", user)
        ));
        userRepository.save(user);
    }
}
