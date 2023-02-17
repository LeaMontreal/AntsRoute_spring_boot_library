package com.lijun.springbootlibrary.dao;

import com.lijun.springbootlibrary.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO S27 12 Create Message Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
