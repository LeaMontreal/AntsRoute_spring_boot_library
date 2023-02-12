package com.lijun.springbootlibrary.responsemodels;

import com.lijun.springbootlibrary.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

// TODO S25 21 Create Response Model
@Data
@AllArgsConstructor
public class ShelfCurrentLoansResponse {
  private Book book;
  private int daysLeft;
}
