package com.tekmentors.authentication;

import com.tekmentors.authentication.model.CustomUserDetails;
import com.tekmentors.authentication.model.User;
import com.tekmentors.authentication.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Supplier<? extends Throwable> s = () -> new UsernameNotFoundException("User doesn't exist");
        User user = userRepository.findUserByUsername(username).orElseThrow(s);

        return new CustomUserDetails(user);
    }
}
