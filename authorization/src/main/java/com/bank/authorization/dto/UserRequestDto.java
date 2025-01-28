package com.bank.authorization.dto;

public class UserRequestDto {
    public String role;
    public Long profileId;
    public String password;

    public UserRequestDto(String role, Long profileId, String password) {
        this.role = role;
        this.profileId = profileId;
        this.password = password;
    }
}
