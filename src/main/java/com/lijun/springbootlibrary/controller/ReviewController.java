package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.requestmodels.ReviewRequest;
import com.lijun.springbootlibrary.service.ReviewService;
import com.lijun.springbootlibrary.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
  private ReviewService reviewService;

  public ReviewController (ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping("/secure")
  public void postReview(@RequestHeader(value="Authorization") String token,
                         @RequestBody ReviewRequest reviewRequest) throws Exception {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    if (userEmail == null) {
      throw new Exception("User email is missing");
    }
    reviewService.postReview(userEmail, reviewRequest);
  }

}
