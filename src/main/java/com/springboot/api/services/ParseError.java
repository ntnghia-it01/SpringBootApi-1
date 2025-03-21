package com.springboot.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class ParseError {
  
  public void parseError(Errors errors, List<String> fields) throws Exception{
    if(errors.hasErrors()){
      // fields => danh sach bien can kiem tra loi "username", "password"
      // for duyet qua fields
      // if(errors.getFieldError(item) != null)
      // => Exception errors.getFieldError(item).message

      for(String field : fields){
        if(errors.getFieldError(field) != null){
          throw new Exception(errors.getFieldError(field).getDefaultMessage());
        }
      }
    }
  }
}
