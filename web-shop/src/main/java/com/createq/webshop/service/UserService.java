package com.createq.webshop.service;

import com.createq.webshop.dto.UserRegistrationDTO;
import com.createq.webshop.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService{
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findById(Long userId);
    List<UserModel> findAllUsers();
    void deleteUser(Long userId);
    UserModel registerNewUser(UserRegistrationDTO registrationDto);
}
