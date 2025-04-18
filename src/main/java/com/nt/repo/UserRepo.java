package com.nt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	User findByUsername(String username);

}
