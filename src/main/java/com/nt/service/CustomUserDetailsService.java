package com.nt.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nt.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.isUser(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
        	    user.getUsername(),
        	    user.getPassword(),
        	    user.getRoles().stream()
        	        .map(role -> new SimpleGrantedAuthority(role.getName()))
        	        .collect(Collectors.toList())
        	);

    }
}
