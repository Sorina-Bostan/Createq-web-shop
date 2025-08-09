package com.createq.webshop.converter;

import com.createq.webshop.dto.ProductDTO;
import com.createq.webshop.model.ProductModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductConverter {
    public ProductDTO convert(ProductModel productModel){
        if(productModel == null) {
            return null;
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productModel.getId());
        productDTO.setName(productModel.getName());
        productDTO.setDescription(productModel.getDescription());
        productDTO.setPrice(productModel.getPrice());
        productDTO.setImageUrl(productModel.getImageUrl());
        productDTO.setCategoryName(productModel.getCategory().getName());
        productDTO.setStockQuantity(productModel.getStockQuantity());
        if (productModel.getCategory() != null) {
            productDTO.setCategoryId(productModel.getCategory().getId());
            productDTO.setCategoryName(productModel.getCategory().getName());
        }
        return productDTO;
    }
    public List<ProductDTO> convertAll(List<ProductModel> productsModel) {
        if(productsModel == null) { return null;}
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(ProductModel productModel : productsModel) {
            productDTOList.add(convert(productModel));
        }
        return productDTOList;
    }
}
