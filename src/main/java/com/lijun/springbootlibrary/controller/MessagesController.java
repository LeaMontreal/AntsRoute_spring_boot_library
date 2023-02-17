package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.entity.Message;
import com.lijun.springbootlibrary.service.MessagesService;
import com.lijun.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

// TODO S27 14 Message Controller
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/messages")
public class MessagesController {
  @Value("${myDebugForOkta}")
  private String myDebugForOkta;

  private MessagesService messagesService;

  @Autowired
  public MessagesController(MessagesService messagesService) {
    this.messagesService = messagesService;
  }

  @PostMapping("/secure/add/message")
  public void postMessage(@RequestHeader(value="Authorization") String token,
                          @RequestBody Message messageRequest) {
    String userEmail = "";
    if (myDebugForOkta.equals("true")) {
      userEmail = "watera@gmail.com";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }
    messagesService.postMessage(messageRequest, userEmail);
  }
}
