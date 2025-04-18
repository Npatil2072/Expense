package com.nt.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String password;
	
	private String email;
    @ManyToMany(fetch = FetchType.EAGER)
	  @JoinTable(name = "user_roles",
		        joinColumns = @JoinColumn(name = "user_id"),
		        inverseJoinColumns = @JoinColumn(name = "role_id"))
		    private Set<Role> roles = new HashSet<>();
}
