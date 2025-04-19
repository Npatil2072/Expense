package com.nt.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nt.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByTitleContainingIgnoreCase(String title);

    List<Expense> findByCategory(Category category);

  //  List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

//    @Query("SELECT e FROM Expense e WHERE e.amount BETWEEN :min AND :max")
//    List<Expense> findByAmountRange(Double min, Double max);
}

