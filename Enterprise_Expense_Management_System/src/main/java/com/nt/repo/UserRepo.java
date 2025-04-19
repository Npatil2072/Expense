package com.nt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmailIgnoreCase(String email);


}
