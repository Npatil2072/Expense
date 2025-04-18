package com.nt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(String string);

}
