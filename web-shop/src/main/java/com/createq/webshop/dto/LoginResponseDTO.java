package com.createq.webshop.dto;

public class LoginResponseDTO {
    private String message;
    private String username;
    private boolean isAdmin;

    public LoginResponseDTO(String s, String name, boolean isAdmin) {
        this.message = s;
        this.username = name;
        this.isAdmin = isAdmin;
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
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
