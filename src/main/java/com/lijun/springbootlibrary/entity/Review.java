package com.lijun.springbootlibrary.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Review {
  private Long id;
  private String userEmail;
  private Date date;
  private double rating;
  private Long bookId;
  private String reviewDescription;
}
