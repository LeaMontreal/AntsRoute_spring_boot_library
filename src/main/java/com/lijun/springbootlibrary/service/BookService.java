package com.lijun.springbootlibrary.service;

import com.lijun.springbootlibrary.dao.BookRepository;
import com.lijun.springbootlibrary.dao.CheckoutRepository;
import com.lijun.springbootlibrary.dao.HistoryRepository;
import com.lijun.springbootlibrary.entity.Book;
import com.lijun.springbootlibrary.entity.Checkout;
import com.lijun.springbootlibrary.entity.History;
import com.lijun.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

// TODO S19 13.1 Step 3: Create Book Service
@Service
@Transactional
public class BookService {
  // TODO S19 13.2 dependency injection
  private BookRepository bookRepository;

  private CheckoutRepository checkoutRepository;

  // TODO S26 13.1 dependency injection
  private HistoryRepository historyRepository;

  public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository, HistoryRepository historyRepository) {
    this.bookRepository = bookRepository;
    this.checkoutRepository = checkoutRepository;
    this.historyRepository = historyRepository;
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

  // TODO S25 22 Add Current Loans Service function
  public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {
    List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

    // TODO S25 22.1 find a bookId list that the user has checked out
    List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
    List<Long> bookIdList = new ArrayList<>();

    for (Checkout i: checkoutList) {
      bookIdList.add(i.getBookId());
    }

    // TODO S25 22.2 find a book list that matches the bookId list
    List<Book> books = bookRepository.findBooksByBookIds(bookIdList);

    // TODO S25 22.3 iterate each book, calculate how many days the book has already been checked out. Generate a new Response object and add it into the output list: shelfCurrentLoansResponses
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (Book book : books) {
      Optional<Checkout> checkout = checkoutList.stream()
              .filter(x -> x.getBookId() == book.getId()).findFirst();

      if (checkout.isPresent()) {
        Date d1 = sdf.parse(checkout.get().getReturnDate());
        Date d2 = sdf.parse(LocalDate.now().toString());

        TimeUnit time = TimeUnit.DAYS;

        long difference_In_Time = time.convert(d1.getTime() - d2.getTime(),
                TimeUnit.MILLISECONDS);

        shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book, (int) difference_In_Time));
      }
    }

    return shelfCurrentLoansResponses;
  }

  // TODO S25 41 Add Return Book Service
  public void returnBook (String userEmail, Long bookId) throws Exception {
    // check we have this bookId in database checkout table and book table
    Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

    Optional<Book> book = bookRepository.findById(bookId);

    if (!book.isPresent() || validateCheckout == null) {
      throw new Exception("Book does not exist or not checked out by user");
    }

    // change available copies
    book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

    // save into book table, delete record from checkout table
    bookRepository.save(book.get());
    checkoutRepository.deleteById(validateCheckout.getId());

    // TODO S26 13.2 save book and actual return date into database
    History history = new History(
            userEmail,
            validateCheckout.getCheckoutDate(),
            LocalDate.now().toString(),
            book.get().getTitle(),
            book.get().getAuthor(),
            book.get().getDescription(),
            book.get().getImg()
    );
    historyRepository.save(history);
  }

  // TODO S25 51 Add Renew Loan Service
  public void renewLoan(String userEmail, Long bookId) throws Exception {
    // check the bookId is in the database checkout table
    Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
    if (validateCheckout == null) {
      throw new Exception("Book does not exist or not checked out by user");
    }

    // check the days of the loan, user cannot renew a loan if the loan has already been late
    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
    Date d2 = sdFormat.parse(LocalDate.now().toString());

    if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
      // change return date and save the new date into database
      validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
      checkoutRepository.save(validateCheckout);
    }
  }
}
