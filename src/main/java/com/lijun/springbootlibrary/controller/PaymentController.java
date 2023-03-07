package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.requestmodels.PaymentInfoRequest;
import com.lijun.springbootlibrary.service.PaymentService;
import com.lijun.springbootlibrary.utils.ExtractJWT;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

// TODO S34 38 Create Payment Controller
@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/v1/payment/secure")
public class PaymentController {
  private Logger logger = Logger.getLogger(getClass().getName());

  @Value("${myDebugForOkta}")
  private String myDebugForOkta;

  private PaymentService paymentService;

  @Autowired
  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  // TODO S34 38.2 Add endpoint “/payment-intent” to send paymentIntent request and response
  @PostMapping("/payment-intent")
  public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest)
          throws StripeException {
    logger.info("paymentInfo.amount: " + paymentInfoRequest.getAmount());

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
    String userEmail = getUserEmail(token);
    if (userEmail == null) {
      throw new Exception("User email is missing");
    }
    return paymentService.stripePayment(userEmail);
  }

  private String getUserEmail(String token){
    String userEmail;
    if (myDebugForOkta.equals("true")) {
      userEmail = "watera@gmail.com";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }

    return userEmail;
  }
}
