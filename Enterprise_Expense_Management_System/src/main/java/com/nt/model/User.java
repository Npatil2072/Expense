package com.nt.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nt.security.RoleSetDeserializer;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private String email;
	//@JsonIgnore
	private String password;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")  // Reference to the role
    @JsonDeserialize(using = RoleSetDeserializer.class) // Optional, if you still need custom deserialization
    private Role role;  // Only one role per user


}


