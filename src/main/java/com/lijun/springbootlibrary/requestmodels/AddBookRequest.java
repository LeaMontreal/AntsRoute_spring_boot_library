package com.lijun.springbootlibrary.requestmodels;

import lombok.Data;

// TODO S29 11 data model for sending request of Adding a new book
@Data
public class AddBookRequest {
  private String title;
  private String author;
  private String description;
  private int copies;
  private String category;
  private String img;
}

