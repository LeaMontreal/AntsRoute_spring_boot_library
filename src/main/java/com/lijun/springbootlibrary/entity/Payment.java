package com.lijun.springbootlibrary.entity;

import lombok.Data;

import javax.persistence.*;

// TODO S34 34 Create payment entity
@Entity
@Table(name = "payment")
@Data
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name="user_email")
  private String userEmail;

  @Column(name = "amount")
  private double amount;
}

