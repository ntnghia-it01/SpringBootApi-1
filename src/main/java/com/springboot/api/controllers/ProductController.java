package com.springboot.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.api.entities.Product;
import com.springboot.api.entities.User;
import com.springboot.api.jpas.ImageJPA;
import com.springboot.api.jpas.ProductJPA;
import com.springboot.api.response.Response;
import com.springboot.api.response.UserRes;
import com.springboot.api.services.ConvertResponse;
import com.springboot.api.services.ImageServices;

@CrossOrigin("*")
@RestController
@RequestMapping("/product")
public class ProductController {
  
  @Autowired
  ProductJPA productJPA;

  @Autowired
  ImageJPA imageJPA;

  @Autowired
  ImageServices imageServices;

  @Autowired
  ConvertResponse convertResponse;

  @GetMapping("/list")
  public ResponseEntity<Response> getAll(){
    Response res = new Response();
		try{
			List<Product> products = productJPA.findAll();

			res.setStatus(1);
			res.setMessage("Success!");
      res.setData(convertResponse.convertProdEntityListToRes(products));

      return ResponseEntity.ok().body(res);
		}catch(Exception e){
			res.setData(null);
			res.setStatus(0);
			res.setMessage("Error!");
			return ResponseEntity.badRequest().body(res);
		}
  }
}
