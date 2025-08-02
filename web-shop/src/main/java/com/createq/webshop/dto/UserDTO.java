package com.createq.webshop.dto;


public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName; // <-- Câmp nou adăugat
    private String lastName;
    private String role;
    private Boolean verified;
    //private CartDTO cart;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}
    public Boolean getVerified() {return verified;}
    public void setVerified(Boolean verified) {this.verified = verified;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
}
