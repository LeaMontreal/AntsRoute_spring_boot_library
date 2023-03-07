package com.lijun.springbootlibrary.service;

import com.lijun.springbootlibrary.dao.PaymentRepository;
import com.lijun.springbootlibrary.entity.Payment;
import com.lijun.springbootlibrary.requestmodels.PaymentInfoRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentService {
  // TODO S34 37.1 Injection dependency
  private PaymentRepository paymentRepository;

  @Autowired
  // read stripe secret key from application.properties
  public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.key.secret}") String secretKey) {
    this.paymentRepository = paymentRepository;
    // initialize Stripe API with secret key
    Stripe.apiKey = secretKey;
  }

  // TODO S34 37.2 Create PaymentIntent, send POST request to stripe.com
  public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException {
    List<String> paymentMethodTypes = new ArrayList<>();
    // "card" for credit card
    paymentMethodTypes.add("card");

    Map<String, Object> params = new HashMap<>();
    params.put("amount", paymentInfoRequest.getAmount());
    params.put("currency", paymentInfoRequest.getCurrency());
    params.put("payment_method_types", paymentMethodTypes);
    // add store info to “description”
    params.put("description", "Ants Route Library");

    params.put("receipt_email", paymentInfoRequest.getReceiptEmail());

    // send request to stripe.com
    return PaymentIntent.create(params);
  }

  // TODO S34 37.3 Create Payment complete Service. After React send purchase request to stripe.com successfully, React will send request to change database record ( reset amount into 0).
  public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
    Payment payment = paymentRepository.findByUserEmail(userEmail);

    if (payment == null) {
      throw new Exception("Payment information is missing");
    }

    // Change amount into 0 after React send purchase request to stripe.com successfully
    payment.setAmount(00.00);
    paymentRepository.save(payment);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
