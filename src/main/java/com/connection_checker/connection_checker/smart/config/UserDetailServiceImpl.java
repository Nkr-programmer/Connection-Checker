package com.connection_checker.connection_checker.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.connection_checker.connection_checker.dao.UserRepository;
import com.connection_checker.connection_checker.entities.User;


public class UserDetailServiceImpl implements UserDetailsService{

   @Autowired
   UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserName(username);
        if(user==null){throw new UsernameNotFoundException("Coudnt found username");}
         
    CustomUserDetails customUserDetails = new CustomUserDetails(user);
    return customUserDetails;
    }
    
}
