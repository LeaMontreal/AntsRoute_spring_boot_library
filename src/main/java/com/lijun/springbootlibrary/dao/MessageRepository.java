package com.lijun.springbootlibrary.dao;

import com.lijun.springbootlibrary.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

// TODO S27 12 Create Message Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  // TODO S27 33.1 Add interface for search all messages of a specific user in MessageRepository
  Page<Message> findByUserEmail(@RequestParam("userEmail") String userEmail, Pageable pageable);

  // TODO S28 Add interface for search all closed or open messages
  Page<Message> findByClosed(@RequestParam("closed") boolean closed, Pageable pageable);
}
