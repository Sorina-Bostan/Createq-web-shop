package com.createq.webshop.controllers;

import com.createq.webshop.dto.AdminCreateUserDTO;
import com.createq.webshop.dto.UpdateUserRoleDTO;
import com.createq.webshop.dto.UserDTO;
import com.createq.webshop.facades.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserAdminController {
    private final UserFacade userFacade;

    @Autowired
    public UserAdminController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userFacade.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody AdminCreateUserDTO userDTO) {
        UserDTO createdUser = userFacade.createUserByAdmin(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long userId, @RequestBody UpdateUserRoleDTO roleDTO) {
        UserDTO updatedUser = userFacade.updateUserRole(userId, roleDTO.getNewRole());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userFacade.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
