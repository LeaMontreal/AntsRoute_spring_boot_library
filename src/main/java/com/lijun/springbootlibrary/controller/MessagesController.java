package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.entity.Message;
import com.lijun.springbootlibrary.service.MessagesService;
import com.lijun.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// TODO S27 14 Message Controller
@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {
  private MessagesService messagesService;

  @Autowired
  public MessagesController(MessagesService messagesService) {
    this.messagesService = messagesService;
  }

  @PostMapping("/secure/add/message")
  public void postMessage(@RequestHeader(value="Authorization") String token,
                          @RequestBody Message messageRequest) {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    messagesService.postMessage(messageRequest, userEmail);
  }
}
