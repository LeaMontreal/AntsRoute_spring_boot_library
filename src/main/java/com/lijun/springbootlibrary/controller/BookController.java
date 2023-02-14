package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.entity.Book;
import com.lijun.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;
import com.lijun.springbootlibrary.service.BookService;
import com.lijun.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO S19 14.1 Step 4: Create Book Controller
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
  @Value("${myDebugForOkta}")
  private String myDebugForOkta;

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  // TODO S25 23 Add controller function and Endpoint "/secure/currentloans"
  @GetMapping("/secure/currentloans")
  public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token)
          throws Exception
  {
    String userEmail = "";
    if (myDebugForOkta.equals("true")) {
      userEmail = "watera@gmail.com";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }

    return bookService.currentLoans(userEmail);
  }

  // TODO S19 14.2 Implement function for PUT request "/secure/checkout"
  @PutMapping("/secure/checkout")
  public Book checkoutBook(@RequestHeader(value = "Authorization") String token,@RequestParam Long bookId) throws Exception{
    // TODO S20 23 change controller to read user’s email from request header
    String userEmail = "";
    if (myDebugForOkta.equals("true")) {
      userEmail = "watera@gmail.com";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }

    return bookService.checkoutBook(userEmail, bookId);
  }

  // TODO S19 21.2, if the specific book has been checked out by the user
  @GetMapping("/secure/ischeckedout/byuser")
  public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) {
    String userEmail = "";
    if (myDebugForOkta.equals("true")) {
      userEmail = "watera@gmail.com";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }

    return bookService.checkoutBookByUser(userEmail, bookId);
  }

  // TODO S19 22.3 how many books user already checked out
  @GetMapping("/secure/currentloans/count")
  public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
    String userEmail = "";
    if (myDebugForOkta.equals("true")) {
      userEmail = "watera@gmail.com";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }
    return bookService.currentLoansCount(userEmail);
  }

  // TODO S25 42 Add returnBook endpoint
  @PutMapping("/secure/return")
  public void returnBook(@RequestHeader(value = "Authorization") String token,
                         @RequestParam Long bookId) throws Exception {
    String userEmail = "";
    if (myDebugForOkta.equals("true")) {
      userEmail = "watera@gmail.com";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }
    bookService.returnBook(userEmail, bookId);
  }

  // TODO S25 52 Add Endpoint to Renew Loan
  @PutMapping("/secure/renew/loan")
  public void renewLoan(@RequestHeader(value = "Authorization") String token,
                        @RequestParam Long bookId) throws Exception {
    String userEmail = "";
    if (myDebugForOkta.equals("true")) {
      userEmail = "watera@gmail.com";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }

    bookService.renewLoan(userEmail, bookId);
  }
}
