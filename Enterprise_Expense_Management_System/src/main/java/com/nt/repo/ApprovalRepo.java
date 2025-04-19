package com.nt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.model.Approval;

public interface ApprovalRepo  extends JpaRepository<Approval, Long> {
    List<Approval> findByStatus(String status);
}