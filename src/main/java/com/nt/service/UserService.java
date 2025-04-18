package com.nt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
      public Optional<User> isUser(String username) {
    	    if (username == null || username.trim().isEmpty()) {
    	        return Optional.empty();
    	    }
    	    return Optional.ofNullable(repo.findByUsername(username));
    	}


	
      
}
