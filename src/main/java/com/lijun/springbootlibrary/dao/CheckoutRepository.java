package com.lijun.springbootlibrary.dao;

import com.lijun.springbootlibrary.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// TODO S19 12 Step 2: Add CheckoutRepository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
  Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

  // TODO S19 22.1, tell how many books user already checked out
  List<Checkout> findBooksByUserEmail(String userEmail);

}
