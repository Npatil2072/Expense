package com.nt.security;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.model.AuthRequest;
import com.nt.model.AuthResponse;
import com.nt.model.Role;
import com.nt.model.User;
import com.nt.repo.RoleRepo;
import com.nt.repo.UserRepo;
import com.nt.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired UserRepo userRepository;
	@Autowired UserService userService;
	@Autowired RoleRepo roleRepository;
	
	

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during login: " + e.getMessage());
        }
    }

    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.isUser(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
     //   Assign default role (e.g., ROLE_EMPLOYEE)
        Optional<Role> roleOpt = roleRepository.findByName("Employee");
        if (roleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Default role not found.");
        }
        user.setRoles(Set.of(roleOpt.get()));


        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
