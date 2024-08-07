package com.dihari.majduri.DihariMajduri.security.service;

import com.dihari.majduri.DihariMajduri.mobile.dao.FarmerRepository;
import com.dihari.majduri.DihariMajduri.mobile.model.Farmer;
import com.dihari.majduri.DihariMajduri.security.model.MobileUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Primary
public class MobileUserDetailsService implements UserDetailsService {
    @Autowired
    FarmerRepository farmerRepository;
    @Override
    public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {
        Optional<Farmer> farmer=farmerRepository.findByMobileNumber(mobileNumber);

        if(farmer.isEmpty()){
            throw  new UsernameNotFoundException("Mobile number :"+mobileNumber +" not found");
        }
        return new MobileUserPrincipal(farmer.orElse(null));

    }
}
