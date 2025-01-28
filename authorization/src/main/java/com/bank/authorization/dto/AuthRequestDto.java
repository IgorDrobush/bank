package com.bank.authorization.model.dto;

public class AuthRequestDto {

    public String username;

    public String password;

    public AuthRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
