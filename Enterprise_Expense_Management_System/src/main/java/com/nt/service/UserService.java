package com.nt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nt.model.User;
import com.nt.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
      public List<User> getUserDetails(){
    	  return repo.findAll();
      }
      //Optional<User> opt=Optional.of(repo.findByUsername(user));
//      public Optional<User> isUser(String email) {
//    	    if (email == null || email.trim().isEmpty()) {
//    	        return Optional.empty();
//    	    }
//    	    return Optional.ofNullable(repo.findByEmail(email));
//    	}


	
      
}
