package com.springboot.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.api.beans.UserBean;
import com.springboot.api.entities.User;
import com.springboot.api.response.Response;
import com.springboot.api.response.UserRes;
import com.springboot.api.services.ParseError;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	/*
		* GET => Lấy dữ liệu POST => Lưu dữ liệu mới PUT => Sửa dữ liệu DELETE => Xoá
		* dữ liệu
		*/

//    SOAP API

	@Autowired
	ParseError parseError;

	@GetMapping("/user")
	public ResponseEntity<Response> getInfo() {
		try {
			User userEntity = new User(1, "username", "123456", "test"); // jpa get db

			// convert entity => response

			UserRes userRes = new UserRes(userEntity.getId(), userEntity.getUsername(), userEntity.getName());
			Integer.parseInt("abc");
			Response response = new Response();
			response.setStatus(0);
			response.setMessage("Success!");
			response.setData(userRes);

			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {

			Response response = new Response();
			response.setStatus(1);
			response.setMessage(e.getMessage());
			response.setData(null);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
  }

	@PostMapping("/user")
	public ResponseEntity<Response> saveUser(@Valid UserBean userBean, Errors errors){
		// errors.hasErrors() == true có lỗi
		
		try{
			List<String> fields = new ArrayList<>();
			fields.add("username");
			fields.add("password");

			// thư viện gson
			// userBean => json
			// get keys => list ("username", "password", ...)

			parseError.parseError(errors, fields);

			Response response = new Response();
			response.setStatus(0);
			response.setMessage("Success!");
			response.setData(null);

			return ResponseEntity.ok(response);

		}catch(Exception e){
			Response responseError = new Response();
			responseError.setStatus(1);
			responseError.setMessage(e.getMessage());
			responseError.setData(null);

			return ResponseEntity.badRequest().body(responseError);
		}
	}

}
