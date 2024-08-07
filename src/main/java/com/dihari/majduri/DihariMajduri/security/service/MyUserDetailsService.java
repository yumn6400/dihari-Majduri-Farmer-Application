package com.dihari.majduri.DihariMajduri.security.service;

import com.dihari.majduri.DihariMajduri.web.dao.UserRepository;
import com.dihari.majduri.DihariMajduri.web.model.User;
import com.dihari.majduri.DihariMajduri.security.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if(user==null){
            throw  new UsernameNotFoundException("User :"+username +" not found");
        }
        return new UserPrincipal(user);

    }
}
