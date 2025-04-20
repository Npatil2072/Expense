package com.nt.security;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.model.AuthRequest;
import com.nt.model.Role;
import com.nt.model.User;
import com.nt.repo.RoleRepo;
import com.nt.repo.UserRepo;
import com.nt.service.CustomUserDetailsService;
import com.nt.service.UserService;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
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
    
    @Autowired
    private CustomUserDetailsService userDetailsService;

	/*    @PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
	    try {
	        Authentication authentication = authManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        String token = jwtService.generateToken(userDetails);
	        return ResponseEntity.ok(new AuthResponse(token));
	    } catch (BadCredentialsException e) {
	        return ResponseEntity.status(401).body("Invalid credentials.");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Error during login: " + e.getMessage());
	    }
	}*/

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = userOpt.get();
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(Map.of("token", token));
    }

    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
     //   Assign default role (e.g., ROLE_EMPLOYEE)
        Optional<Role> roleOpt = roleRepository.findByName("Employee");
        if (roleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Default role not found.");
        }
        user.setRole(roleOpt.get());  // not setRoles




        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
