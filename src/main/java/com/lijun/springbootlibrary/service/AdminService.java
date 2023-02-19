package com.lijun.springbootlibrary.service;

import com.lijun.springbootlibrary.dao.BookRepository;
import com.lijun.springbootlibrary.entity.Book;
import com.lijun.springbootlibrary.requestmodels.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO S29 12 New AdminService, inject dependency
@Service
@Transactional
public class AdminService {
  private BookRepository bookRepository;

  @Autowired
  public AdminService (BookRepository bookRepository){
    this.bookRepository = bookRepository;
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
