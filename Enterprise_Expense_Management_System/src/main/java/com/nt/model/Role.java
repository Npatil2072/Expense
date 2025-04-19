package com.nt.model;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    
	@Id
	@GeneratedValue
	private Long id;
	private String name;
//	public Role orElseThrow() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
    public String getAuthority() {
        return name;
    }
}
