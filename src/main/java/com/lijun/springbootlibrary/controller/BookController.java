package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.entity.Book;
import com.lijun.springbootlibrary.service.BookService;
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
  public Book checkoutBook(@RequestParam Long bookId) throws Exception{
    String userEmail = "testuser1@email.com";
    return bookService.checkoutBook(userEmail, bookId);
  }

  // TODO S19 21.2, if the specific book has been checked out by the user
  @GetMapping("/secure/ischeckedout/byuser")
  public Boolean checkoutBookByUser(@RequestParam Long bookId) {
    String userEmail = "testuser1@email.com";
    return bookService.checkoutBookByUser(userEmail, bookId);
  }

  // TODO S19 22.3 how many books user already checked out
  @GetMapping("/secure/currentloans/count")
  public int currentLoansCount() {
    String userEmail = "testuser1@email.com";
    return bookService.currentLoansCount(userEmail);
  }

}
