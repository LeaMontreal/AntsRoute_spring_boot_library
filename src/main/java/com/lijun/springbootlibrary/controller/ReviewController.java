package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.requestmodels.ReviewRequest;
import com.lijun.springbootlibrary.service.ReviewService;
import com.lijun.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
  @Value("${myDebugForOkta}")
  private String myDebugForOkta;

  private ReviewService reviewService;

  public ReviewController (ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @GetMapping("/secure/user/book")
  public Boolean reviewBookByUser(@RequestHeader(value="Authorization") String token,
                                  @RequestParam Long bookId) throws Exception {
    String userEmail = "";
    if (myDebugForOkta.equals("true")) {
      userEmail = "watera@gmail.com";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }

    if (userEmail == null) {
      throw new Exception("User email is missing");
    }
    return reviewService.userReviewListed(userEmail, bookId);
  }

  @PostMapping("/secure")
  public void postReview(@RequestHeader(value="Authorization") String token,
                         @RequestBody ReviewRequest reviewRequest) throws Exception {
    String userEmail = "";
    if (myDebugForOkta.equals("true")) {
      userEmail = "watera@gmail.com";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }
    if (userEmail == null) {
      throw new Exception("User email is missing");
    }
    reviewService.postReview(userEmail, reviewRequest);
  }

}
