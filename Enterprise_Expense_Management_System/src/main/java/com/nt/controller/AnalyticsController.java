package com.nt.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.model.Expense;
import com.nt.repo.ExpenseRepo;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    @Autowired private ExpenseRepo repo;

    @GetMapping("/monthly/{userId}")
    public Map<String, Double> getMonthlyExpenses(@PathVariable Long userId) {
        List<Expense> expenses = repo.findByUserId(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return expenses.stream()
                .collect(Collectors.groupingBy(
                    e -> LocalDate.parse(e.getDate(), formatter).getMonth().toString(), // Extracts month from date
                    Collectors.summingDouble(exp -> Double.parseDouble(exp.getAmount()))
                ));
    }

    @GetMapping("/by-category/{userId}")
    public Map<String, Double> getCategoryBreakdown(@PathVariable Long userId) {
        List<Expense> expenses = repo.findByUserId(userId);
        return expenses.stream()
                .collect(Collectors.groupingBy(
                    e -> String.valueOf(e.getCategory()),         // Ensures key is String
                    Collectors.summingDouble(e -> Double.parseDouble(e.getAmount()))
                ));
    }

}