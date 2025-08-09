package com.createq.webshop.facades;

import com.createq.webshop.dto.AdminCreateUserDTO;
import com.createq.webshop.dto.UserDTO;
import com.createq.webshop.dto.UserRegistrationDTO;

import java.util.List;

public interface UserFacade {
    UserDTO registerUser(UserRegistrationDTO registrationDto);
    List<UserDTO> getAllUsers();
    UserDTO createUserByAdmin(AdminCreateUserDTO userDTO);
    UserDTO updateUserRole(Long userId, String newRole);
    void deleteUser(Long userId);
}
