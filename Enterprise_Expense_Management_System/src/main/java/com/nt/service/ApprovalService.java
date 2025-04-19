package com.nt.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.model.Approval;
import com.nt.repo.ApprovalRepo;

@Service
public class ApprovalService {
    @Autowired private ApprovalRepo repo;

    public Approval approve(Long expenseId, String role) {
        Approval approval = repo.findById(expenseId).orElseThrow();
        approval.setStatus("APPROVED");
        approval.setApproverRole(role);
        approval.setApprovedAt(LocalDateTime.now());
        return repo.save(approval);
    }
}
