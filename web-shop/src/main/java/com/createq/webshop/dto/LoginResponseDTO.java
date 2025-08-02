package com.createq.webshop.dto;

public class LoginResponseDTO {
    private String message;
    private String username;

    public LoginResponseDTO(String s, String name) {
        this.message = s;
        this.username = name;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
