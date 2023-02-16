package com.lijun.springbootlibrary.dao;

import com.lijun.springbootlibrary.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

// TODO S26 12 Create History Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

  Page<History> findBooksByUserEmail(@RequestParam("email")String userEmail, Pageable pageable);
}
