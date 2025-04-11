package com.springboot.api.jpas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api.entities.Image;

public interface ImageJPA extends JpaRepository<Image, Integer>{
  
}
