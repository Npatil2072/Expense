package com.nt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.model.Approval;
import com.nt.service.ApprovalService;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {
    @Autowired private ApprovalService service;

    @PostMapping("/{expenseId}/{role}")
    public ResponseEntity<Approval> approve(@PathVariable Long expenseId, @PathVariable String role) {
        return ResponseEntity.ok(service.approve(expenseId, role));
    }
}