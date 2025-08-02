package com.createq.webshop.converter;

import com.createq.webshop.dto.CartItemDTO;
import com.createq.webshop.dto.ProductDTO;
import com.createq.webshop.model.CartItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartItemConverter {
    private final ProductConverter productConverter;
    @Autowired
    public CartItemConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }
    public CartItemDTO convert(CartItemModel cartItemModel) {
        if(cartItemModel == null) { return null;}
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItemModel.getId());
        cartItemDTO.setQuantity(cartItemModel.getQuantity());
        if (cartItemModel.getProduct() != null) {
            cartItemDTO.setProductId(cartItemModel.getProduct().getId());
        }
        return cartItemDTO;
    }
    public List<CartItemDTO> convertAll(List<CartItemModel> items) {
        if (items == null) return null;
        return items.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
    public CartItemModel toModel(CartItemDTO dto) {
        if (dto == null) return null;

        CartItemModel model = new CartItemModel();
        model.setId(dto.getId());
        model.setQuantity(dto.getQuantity());
        return model;
    }

    public List<CartItemModel> toModelList(List<CartItemDTO> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
