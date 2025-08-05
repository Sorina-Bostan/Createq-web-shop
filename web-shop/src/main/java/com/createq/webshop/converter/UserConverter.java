package com.createq.webshop.converter;

import com.createq.webshop.dto.CartDTO;
import com.createq.webshop.dto.UserDTO;
import com.createq.webshop.model.CartModel;
import com.createq.webshop.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {
    @Autowired
    private CartConverter cartConverter;
    public UserDTO convert(UserModel user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setVerified(user.getVerified());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        return userDTO;
    }
    public List<UserDTO> convertAll(List<UserModel> users) {
        if(users == null) return null;
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserModel userModel : users) {
            userDTOList.add(convert(userModel));
        }
        return userDTOList;
    }
}
