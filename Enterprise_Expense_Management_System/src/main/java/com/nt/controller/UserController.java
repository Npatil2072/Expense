package com.nt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.model.User;
import com.nt.repo.UserRepo;
import com.nt.service.UserService;

@RestController
@RequestMapping("/home")
public class UserController {
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepo repo;
	
//	@GetMapping("/user")
//	public ResponseEntity<List<User>> userDetails(){
//		List<User> list=service.getUserDetails();
//		return new ResponseEntity<List<User>>(HttpStatus.OK);
//	}

	@GetMapping("/user")
	public ResponseEntity<List<User>> userDetails(){
	//	User user=new User(2L, "Nitin", "Nitin", "Nitin@gmail.com");
		//List<User> list=List.of(new User(1L, "Np", "Np", "Np@gmail.com"),new User(1L, "Npp", "Npp", "Npp@gmail.com"));
	   
		  List<User> list=repo.findAll();
		return new ResponseEntity<List<User>>(list,HttpStatus.OK);
	}
	@PostMapping("/user")
	public String userDetails(User user){
		return 	repo.save(user).getId().toString()+" succes";
	}
	
	
	
}
