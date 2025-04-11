package com.springboot.api.jpas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api.entities.Product;

public interface ProductJPA extends JpaRepository<Product, Integer>{
  
}
