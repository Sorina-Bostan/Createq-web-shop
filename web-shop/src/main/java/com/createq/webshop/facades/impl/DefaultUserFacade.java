package com.createq.webshop.facades.impl;

import com.createq.webshop.converter.UserConverter;
import com.createq.webshop.dto.AdminCreateUserDTO;
import com.createq.webshop.dto.UserDTO;
import com.createq.webshop.dto.UserRegistrationDTO;
import com.createq.webshop.facades.UserFacade;
import com.createq.webshop.model.UserModel;
import com.createq.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultUserFacade implements UserFacade {
    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public DefaultUserFacade(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @Override
    public UserDTO registerUser(UserRegistrationDTO registrationDto) {
        UserModel newUserModel = userService.registerNewUser(registrationDto);
        return userConverter.convert(newUserModel);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserModel> userModels = userService.findAllUsers();
        return userConverter.convertAll(userModels);
    }
    @Override
    public UserDTO createUserByAdmin(AdminCreateUserDTO userDTO) {
        UserModel createdUser = userService.createUserByAdmin(userDTO);
        return userConverter.convert(createdUser);
    }
    @Override
    public UserDTO updateUserRole(Long userId, String newRole) {
        UserModel updatedUser = userService.updateUserRole(userId, newRole);
        return userConverter.convert(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }
}
