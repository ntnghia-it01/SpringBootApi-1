package com.springboot.api.beans;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductBean {
  private Optional<Integer> id;

  @NotNull(message = "Name is Null")
  @NotBlank(message = "Name is Blank")
  private String name;

  @Min(value = 1000, message = "Price is min 10000")
  private int price;

  @Min(value = 1, message = "Quantity is min 1")
  private int quantity;

  List<MultipartFile> images;
}
