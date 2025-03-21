package com.springboot.api.beans;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBean {
  @NotNull(message = "Username is Null")
  @NotBlank(message = "Username is Blank")
  private String username;
  @NotNull(message = "Password is Null")
  @NotBlank(message = "Password is Blank")
  private String password;
  private String name;
  private MultipartFile images;
}
