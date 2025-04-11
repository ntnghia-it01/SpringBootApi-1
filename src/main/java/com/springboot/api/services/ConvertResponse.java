package com.springboot.api.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.api.entities.Image;
import com.springboot.api.entities.Product;
import com.springboot.api.response.ImageRes;
import com.springboot.api.response.ProductRes;

@Service
public class ConvertResponse {
  
  public ProductRes convertProdEntityToRes(Product product){
    ProductRes productRes = new ProductRes();

    productRes.setId(product.getId());
    productRes.setName(product.getName());
    productRes.setPrice(product.getPrice());
    productRes.setQuantity(product.getQuantity());

    List<ImageRes> imageResList = new ArrayList<>();

    for(Image image : product.getImages()){
      ImageRes imageRes = new ImageRes();
      imageRes.setId(image.getId());
      imageRes.setProdId(product.getId());
      imageRes.setImageUrl(String.format("http://172.16.18.45:8080/images/%s", product.getName()));
      imageResList.add(imageRes);
    }

    productRes.setImages(imageResList);

    return productRes;
  }

  public List<ProductRes> convertProdEntityListToRes(List<Product> products){
    List<ProductRes> productRes = new ArrayList<>();

    for(Product product : products){
      productRes.add(convertProdEntityToRes(product));
    }

    return productRes;
  }
}
