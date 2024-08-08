package com.dihari.majduri.DihariMajduri.security.service;

import com.dihari.majduri.DihariMajduri.mobile.dao.FarmerRepository;
import com.dihari.majduri.DihariMajduri.mobile.model.Farmer;
import com.dihari.majduri.DihariMajduri.security.model.MobileUserPrincipal;
import com.dihari.majduri.DihariMajduri.web.dao.UserRepository;
import com.dihari.majduri.DihariMajduri.web.model.User;
import com.dihari.majduri.DihariMajduri.security.model.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
@Primary
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FarmerRepository farmerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String divertTo = (String) request.getAttribute("divertTo");
        if(divertTo.equals("WEB")) {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw  new UsernameNotFoundException("Username :"+username +" not found");
            }
                return new UserPrincipal(user);

        }else if(divertTo.equals("MOBILE")){
            Optional<Farmer> farmer=farmerRepository.findByMobileNumber(username);

            if(farmer.isEmpty()){
                throw  new UsernameNotFoundException("Mobile number :"+username +" not found");
            }
            return new MobileUserPrincipal(farmer.orElse(null));
        }
        throw  new UsernameNotFoundException("Invalid request URL");

    }
}
