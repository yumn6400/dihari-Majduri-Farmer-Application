package com.dihari.majduri.DihariMajduri.web.service;

import com.dihari.majduri.DihariMajduri.web.dao.UserRepository;
import com.dihari.majduri.DihariMajduri.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    public User saveUser(User user){
        User existingUser=userRepository.findByUsername(user.getUsername());
        if(existingUser!=null)
        {
            existingUser.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(existingUser);
        }
        else {
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }
}
