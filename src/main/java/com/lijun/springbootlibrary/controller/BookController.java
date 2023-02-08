package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.entity.Book;
import com.lijun.springbootlibrary.service.BookService;
import com.lijun.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// TODO S19 14.1 Step 4: Create Book Controller
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  // TODO S19 14.2 Implement function for PUT request "/secure/checkout"
  @PutMapping("/secure/checkout")
  public Book checkoutBook(@RequestHeader(value = "Authorization") String token,@RequestParam Long bookId) throws Exception{
    // TODO S20 23 change controller to read user’s email from request header
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    return bookService.checkoutBook(userEmail, bookId);
  }

  // TODO S19 21.2, if the specific book has been checked out by the user
  @GetMapping("/secure/ischeckedout/byuser")
  public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    return bookService.checkoutBookByUser(userEmail, bookId);
  }

  // TODO S19 22.3 how many books user already checked out
  @GetMapping("/secure/currentloans/count")
  public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    return bookService.currentLoansCount(userEmail);
  }

}
