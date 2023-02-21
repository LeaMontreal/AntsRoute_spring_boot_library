package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.entity.Message;
import com.lijun.springbootlibrary.requestmodels.AdminQuestionRequest;
import com.lijun.springbootlibrary.service.MessagesService;
import com.lijun.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

// TODO S27 14 Message Controller
//@CrossOrigin("http://localhost:3000")
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

  // TODO S28 51.3 Add endPoint, check the user’s role, extract “userType” field
  @PutMapping("/secure/admin/message")
  public void putMessage(@RequestHeader(value="Authorization") String token,
                         @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
    String userEmail = "";
    String admin;
    if (myDebugForOkta.equals("true")) {
      userEmail = "adminuser@gmail.com";
      admin = "admin";
    } else {
      userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
      admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
    }

    if (admin == null || !admin.equals("admin")) {
      throw new Exception("Administration page only.");
    }
    messagesService.putMessage(adminQuestionRequest, userEmail);
  }

}
