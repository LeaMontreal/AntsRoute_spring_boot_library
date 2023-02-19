package com.lijun.springbootlibrary.controller;

import com.lijun.springbootlibrary.requestmodels.AddBookRequest;
import com.lijun.springbootlibrary.service.AdminService;
import com.lijun.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

// TODO S29 14 New AdminController, inject dependency
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
  @Value("${myDebugForOkta}")
  private String myDebugForOkta;

  private AdminService adminService;

  @Autowired
  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  // TODO S29 15 Implement controller function
  @PostMapping("/secure/add/book")
  public void postBook(@RequestHeader(value="Authorization") String token,
                       @RequestBody AddBookRequest addBookRequest) throws Exception {
    String admin;
    if (myDebugForOkta.equals("true")) {
      admin = "admin";
    } else {
      admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
    }

    if (admin == null || !admin.equals("admin")) {
      throw new Exception("Administration page only");
    }
    adminService.postBook(addBookRequest);
  }
}
