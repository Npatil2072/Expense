package com.nt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.model.Expense;
import com.nt.model.User;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {
	List<Expense> findByUserId(Long userId);

	List<Expense> findByUser(User user);
}
