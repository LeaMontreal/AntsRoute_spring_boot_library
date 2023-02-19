package com.lijun.springbootlibrary.requestmodels;

import lombok.Data;

// TODO S28 51.1 create class AdminQuestionRequest. This is the request FrontEnd send to BackEnd.
@Data
public class AdminQuestionRequest {

  private Long id;

  private String response;
}
