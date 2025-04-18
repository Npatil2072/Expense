package com.nt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.nt.service.ExpenseService;
import com.nt.service.UserService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired private ExpenseService service;
    @Autowired private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Expense> create(@RequestBody Expense e, Authentication auth) {
        String username = auth.getName(); // Get username from the authenticated principal
        Optional<User> userOpt = userService.isUser(username);
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Or 401/403 based on your logic
        }
        
        User user = userOpt.get();
        e.setUser(user);
        return ResponseEntity.ok(service.createExpense(e));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserExpenses(userId));
    }
}
