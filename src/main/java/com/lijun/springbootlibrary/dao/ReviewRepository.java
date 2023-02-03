package com.lijun.springbootlibrary.dao;

import com.lijun.springbootlibrary.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
