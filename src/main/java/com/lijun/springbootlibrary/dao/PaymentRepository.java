package com.lijun.springbootlibrary.dao;

import com.lijun.springbootlibrary.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO S34 35 Create Payment Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
  Payment findByUserEmail(String userEmail);
}

