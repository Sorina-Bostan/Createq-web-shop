package com.createq.webshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotBlank(message = "First name is required.")
    @Size(max = 30, message = "First name cannot be longer than 30 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 30, message = "Last name cannot be longer than 30 characters.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotBlank(message = "Username is required.")
    @Size(max = 30, message = "Username cannot be longer than 30 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "Username contains invalid characters. Use only letters, numbers, and _ . -")
    private String username;

    @NotBlank(message = "Password is required.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long, and include at least one uppercase letter, one lowercase letter, one digit, and one special character."
    )
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
