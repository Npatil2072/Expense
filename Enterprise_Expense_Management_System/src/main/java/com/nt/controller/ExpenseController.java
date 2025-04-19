package com.nt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.model.Expense;
import com.nt.model.User;
import com.nt.repo.UserRepo;
import com.nt.service.ExpenseService;
import com.nt.service.UserService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired private ExpenseService service;
    @Autowired private UserService userService;
    @Autowired private UserRepo userRepo;

    @PostMapping("/create")
    public ResponseEntity<Expense> create(@RequestBody Expense e, Authentication auth) {
        //String username = ((User) auth).getEmail(); // Get username from the authenticated principal
        String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();

    	Optional<User> userOpt = userRepo.findByEmailIgnoreCase(username);
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Or 401/403 based on your logic
        }
        
        User user = userOpt.get();
        e.setUser(user);
        return ResponseEntity.ok(service.createExpense(e));
    }

	/*    @GetMapping("/user/{userId}")
	public ResponseEntity<List<Expense>> getByUser(@PathVariable Long userId) {
	    return ResponseEntity.ok(service.getUserExpenses(userId));
	}
	   */ 
    @GetMapping("/user")
    public ResponseEntity<List<Expense>> getByUser(Authentication auth) {
        String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();

        Optional<User> userOpt = userRepo.findByEmailIgnoreCase(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized if no user found
        }

        User user = userOpt.get();
        List<Expense> expenses = service.getUserExpenses(user.getId());
        return ResponseEntity.ok(expenses);
    }

    // For getting expenses by user ID (can be useful for admin or debugging)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getByUserId(@PathVariable Long userId) {
        List<Expense> expenses = service.getUserExpenses(userId);
        return ResponseEntity.ok(expenses);
    }
}
