package com.lijun.springbootlibrary.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// TODO S19 11 Step 1: Define Checkout Entity
@Entity
@Table(name = "checkout")
@Data
@NoArgsConstructor
public class Checkout {

  public Checkout(String userEmail, String checkoutDate, String returnDate, Long bookId) {
    this.userEmail = userEmail;
    this.checkoutDate = checkoutDate;
    this.returnDate = returnDate;
    this.bookId = bookId;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_email")
  private String userEmail;

  @Column(name = "checkout_date")
  private String checkoutDate;

  @Column(name = "return_date")
  private String returnDate;

  @Column(name = "book_id")
  private Long bookId;

}
