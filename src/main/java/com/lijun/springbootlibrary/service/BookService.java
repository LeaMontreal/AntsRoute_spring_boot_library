package com.lijun.springbootlibrary.service;

import com.lijun.springbootlibrary.dao.BookRepository;
import com.lijun.springbootlibrary.dao.CheckoutRepository;
import com.lijun.springbootlibrary.entity.Book;
import com.lijun.springbootlibrary.entity.Checkout;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

// TODO S19 13.1 Step 3: Create Book Service
@Service
@Transactional
public class BookService {
  // TODO S19 13.2 dependency injection
  private BookRepository bookRepository;

  private CheckoutRepository checkoutRepository;

  public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository) {
    this.bookRepository = bookRepository;
    this.checkoutRepository = checkoutRepository;
  }

  public Book checkoutBook(String userEmail, Long bookId) throws Exception{
    Optional<Book> book = bookRepository.findById(bookId);

    Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

    // TODO S19 13.3 we only want user to check out a specific book once
    if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
      throw new Exception("Book doesn't exist or already checked out by user");
    }

    // change remain available copies
    book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
    // save into database book table
    bookRepository.save(book.get());

    // create a new checkout instance for current transaction
    Checkout checkout = new Checkout(
            userEmail,
            LocalDate.now().toString(),
            LocalDate.now().plusDays(7).toString(),
            book.get().getId()
    );
    // save into database checkout table
    checkoutRepository.save(checkout);

    return book.get();
  }

  // TODO S19 21.1 if the specific book has been checked out by the user
  public Boolean checkoutBookByUser(String userEmail, Long bookId) {
    Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
    if (validateCheckout != null) {
      return true;
    } else {
      return false;
    }
  }

  // TODO S19 22.2 how many books user already checked out
  public int currentLoansCount(String userEmail) {
    return checkoutRepository.findBooksByUserEmail(userEmail).size();
  }
}
