package com.createq.webshop.facades;

import com.createq.webshop.dto.UserDTO;
import com.createq.webshop.dto.UserRegistrationDTO;

import java.util.List;

public interface UserFacade {
    UserDTO registerUser(UserRegistrationDTO registrationDto);
    List<UserDTO> getAllUsers();
}
