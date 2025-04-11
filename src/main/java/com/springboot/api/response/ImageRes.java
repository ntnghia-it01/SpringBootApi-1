package com.springboot.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageRes {
  private int id;
  private int prodId;
  private String imageUrl;
}
