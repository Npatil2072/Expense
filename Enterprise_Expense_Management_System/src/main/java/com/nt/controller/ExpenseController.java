package com.nt.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nt.model.Expense;
import com.nt.model.User;
import com.nt.repo.ExpenseRepo;
import com.nt.repo.UserRepo;
import com.nt.service.ExpenseService;
import com.nt.service.UserService;

//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired private ExpenseService service;
    @Autowired private UserService userService;
    @Autowired private UserRepo userRepo;
    @Autowired private ExpenseRepo expenseRepo;
    

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

    @PostMapping
    public ResponseEntity<?> addExpense(
            @RequestParam(value = "receipt", required = false) MultipartFile receipt,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("amount") Double amount,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            Principal principal) {
        
        try {
            String email = principal.getName(); // Email from JWT token
            User user = userRepo.findByEmailIgnoreCase(email)
                                .orElseThrow(() -> new RuntimeException("User not found"));

            byte[] receiptData = receipt != null ? receipt.getBytes() : null;

            Expense expense = new Expense(date, amount, category, description, receiptData, user);
            expenseRepo.save(expense);

            return ResponseEntity.ok("Expense added");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to read receipt file");
        }
    }


    
	/*    @GetMapping("/user/{userId}")
	public ResponseEntity<List<Expense>> getByUser(@PathVariable Long userId) {
	    return ResponseEntity.ok(service.getUserExpenses(userId));
	}
	   */ 
  //  @PreAuthorize("hasRole('Admin')") // 🔥 NOT hasRole('ROLE_Employee')
    @GetMapping("/user")
    public ResponseEntity<List<Expense>> getByUser(Authentication auth) {
        String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();

        Optional<User> userOpt = userRepo.findByEmailIgnoreCase(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 if not found
        }

        User user = userOpt.get();
        List<Expense> expenses = service.getUserExpenses(user.getId());
        return ResponseEntity.ok(expenses);
    }


    // For getting expenses by user ID (can be useful for admin or debugging)
	/*    @GetMapping("/user/{userId}")
	public ResponseEntity<List<Expense>> getByUserId(@PathVariable Long userId) {
	    List<Expense> expenses = service.getUserExpenses(userId);
	    return ResponseEntity.ok(expenses);
	}*/
    
    @GetMapping
    public ResponseEntity<List<Expense>> getUserExpenses(Principal principal) {
        String email = principal.getName(); // Get email from JWT
        User user = userRepo.findByEmailIgnoreCase(email)
                            .orElseThrow(() -> new RuntimeException("User not found"));

        List<Expense> expenses = expenseRepo.findByUser(user);
        return ResponseEntity.ok(expenses);
    }

}
