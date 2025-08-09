package com.createq.webshop.service;

import com.createq.webshop.dto.AdminCreateUserDTO;
import com.createq.webshop.dto.UserRegistrationDTO;
import com.createq.webshop.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService{
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findById(Long userId);
    List<UserModel> findAllUsers(); //getAllUsers
    void deleteUser(Long userId);
    UserModel registerNewUser(UserRegistrationDTO registrationDto);
    UserModel updateUserRole(Long userId, String newRole);
    UserModel createUserByAdmin(AdminCreateUserDTO userDTO);
}
