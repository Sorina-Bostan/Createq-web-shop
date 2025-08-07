package com.createq.webshop.service.impl;
import com.createq.webshop.converter.UserConverter;
import com.createq.webshop.dto.AdminCreateUserDTO;
import com.createq.webshop.dto.UserRegistrationDTO;
import com.createq.webshop.exception.ResourceNotFoundException;
import com.createq.webshop.model.CartModel;
import com.createq.webshop.model.UserModel;
import com.createq.webshop.repository.UserRepository;
import com.createq.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public UserModel registerNewUser(UserRegistrationDTO registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new IllegalStateException("Username is already taken.");
        }
        long emailCount = userRepository.countByEmail(registrationDto.getEmail());
        if (emailCount >= 2) {
            throw new IllegalStateException("A maximum of 2 accounts can be registered with this email address.");
        }
        UserModel newUser = new UserModel();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setEmail(registrationDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setFirstName(registrationDto.getFirstName());
        newUser.setLastName(registrationDto.getLastName());
        newUser.setRole("CUSTOMER");
        newUser.setVerified(false);
        CartModel newCart = new CartModel();
        newUser.setCart(newCart);
        newCart.setUser(newUser);
        return userRepository.save(newUser);
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserModel> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }
    @Override
    @Transactional
    public UserModel createUserByAdmin(AdminCreateUserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalStateException("Username is already taken.");
        }
        UserModel newUser = new UserModel();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setVerified(false);
        newUser.setRole(userDTO.getRole());
        CartModel newCart = new CartModel();
        newUser.setCart(newCart);
        newCart.setUser(newUser);

        return userRepository.save(newUser);
    }
    @Override
    @Transactional
    public UserModel updateUserRole(Long userId, String newRole) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        if (!"ADMIN".equals(newRole) && !"CUSTOMER".equals(newRole)) {
            throw new IllegalArgumentException("Invalid role specified.");
        }
        user.setRole(newRole);
        return userRepository.save(user);
    }
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserModel userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (userToDelete.getUsername().equals(currentUsername)) {
            throw new IllegalStateException("You cannot delete your own account.");
        }
        userRepository.deleteById(userId);
    }

}