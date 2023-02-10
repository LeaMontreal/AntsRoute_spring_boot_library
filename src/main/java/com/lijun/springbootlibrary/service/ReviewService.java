package com.lijun.springbootlibrary.service;

import com.lijun.springbootlibrary.dao.ReviewRepository;
import com.lijun.springbootlibrary.entity.Review;
import com.lijun.springbootlibrary.requestmodels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;


@Service
@Transactional
public class ReviewService {
  private ReviewRepository reviewRepository;

  @Autowired
  public ReviewService(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
    Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());
    if (validateReview != null) {
      throw new Exception("Review already created");
    }

    Review review = new Review();
    review.setBookId(reviewRequest.getBookId());
    review.setRating(reviewRequest.getRating());
    review.setUserEmail(userEmail);
    if (reviewRequest.getReviewDescription().isPresent()) {
      // use map() to copy an optional to string
      review.setReviewDescription(reviewRequest.getReviewDescription().map(Object::toString).orElse(null));
    }
    // here Date is from java.sql.Date, we're going to save this into database
    review.setDate(Date.valueOf(LocalDate.now()));

    // save review record into database
    reviewRepository.save(review);
  }
}
