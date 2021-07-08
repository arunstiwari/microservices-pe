package com.tekmentor.customersvc.repository;

import com.tekmentor.customersvc.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

   @Autowired
   UserRepository userRepository;

   @Test
    public void addRecordsInRedis(){

        User user = new User(43l,"User-423");
        userRepository.save(user);


       Iterable<User> all = userRepository.findAll();
       for (User u: all){
           System.out.println("u.getName() = " + u.getName());
       }
   }

}