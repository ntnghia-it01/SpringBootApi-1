package com.springboot.api.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRes {
  private int id;
  private String name;
  private int price;
  private int quantity;
  private List<ImageRes> images;
}
