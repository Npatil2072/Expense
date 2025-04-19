package com.nt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.model.Expense;
import com.nt.repo.ExpenseRepo;

@Service
public class ExpenseService {
	@Autowired
	private ExpenseRepo repo;
	   public Expense createExpense(Expense expense) {
	        return repo.save(expense);
	    }

	    public List<Expense> getUserExpenses(Long userId) {
	        return repo.findByUserId(userId);
	    }
	}

