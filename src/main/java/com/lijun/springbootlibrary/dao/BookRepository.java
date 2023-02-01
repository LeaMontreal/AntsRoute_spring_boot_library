package com.lijun.springbootlibrary.dao;

import com.lijun.springbootlibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface BookRepository extends JpaRepository<Book, Long> {
}
