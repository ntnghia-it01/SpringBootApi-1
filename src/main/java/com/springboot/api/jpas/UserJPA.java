package com.springboot.api.jpas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api.entities.User;

public interface UserJPA extends JpaRepository<User, Integer>{
  Optional<User> findByUsername(String username);
}
