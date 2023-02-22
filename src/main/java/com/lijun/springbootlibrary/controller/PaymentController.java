package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.requestmodels.PaymentInfoRequest;
import com.lijun.springbootlibrary.service.PaymentService;
import com.lijun.springbootlibrary.utils.ExtractJWT;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO S34 38 Create Payment Controller
@RestController
@RequestMapping("/api/v1/payment/secure")
public class PaymentController {
  private PaymentService paymentService;

  @Autowired
  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  // TODO S34 38.2 Add endpoint “/payment-intent” to send paymentIntent request and response
  @PostMapping("/payment-intent")
  public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest)
          throws StripeException {

    // Send request to stripe.com
    PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);

    // Send response to frontend with paymentIntent info
    String paymentStr = paymentIntent.toJson();
    return new ResponseEntity<>(paymentStr, HttpStatus.OK);
  }

  // TODO S34 38.3 Add endpoint “/payment-complete” to change database after payment successfully complete
  @PutMapping("/payment-complete")
  public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value="Authorization") String token)
          throws Exception {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    if (userEmail == null) {
      throw new Exception("User email is missing");
    }
    return paymentService.stripePayment(userEmail);
  }

}
