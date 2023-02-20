package com.lijun.springbootlibrary.service;

import com.lijun.springbootlibrary.dao.BookRepository;
import com.lijun.springbootlibrary.entity.Book;
import com.lijun.springbootlibrary.requestmodels.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// TODO S29 12 New AdminService, inject dependency
@Service
@Transactional
public class AdminService {
  private BookRepository bookRepository;

  @Autowired
  public AdminService (BookRepository bookRepository){
    this.bookRepository = bookRepository;
  }

  // TODO S30 21.1 admin user increase Book Quantity
  public void increaseBookQuantity(Long bookId) throws Exception {
    Optional<Book> book = bookRepository.findById(bookId);

    if (!book.isPresent()) {
      throw new Exception("Book not found");
    }

    book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
    book.get().setCopies(book.get().getCopies() + 1);

    bookRepository.save(book.get());
  }

  // TODO S30 22.1 admin user decrease Book Quantity
  public void decreaseBookQuantity(Long bookId) throws Exception {

    Optional<Book> book = bookRepository.findById(bookId);

    if (!book.isPresent() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0) {
      throw new Exception("Book not found or quantity locked");
    }

    book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
    book.get().setCopies(book.get().getCopies() - 1);

    bookRepository.save(book.get());
  }

  // TODO S29 13 Implement service function
  public void postBook(AddBookRequest addBookRequest) {
    Book book = new Book();

    book.setTitle(addBookRequest.getTitle());
    book.setAuthor(addBookRequest.getAuthor());
    book.setDescription(addBookRequest.getDescription());
    book.setCopies(addBookRequest.getCopies());
    book.setCopiesAvailable(addBookRequest.getCopies());
    book.setCategory(addBookRequest.getCategory());
    book.setImg(addBookRequest.getImg());
    bookRepository.save(book);
  }

}
