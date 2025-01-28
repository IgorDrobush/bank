package com.bank.authorization.model.dto;

public class UserResponseDto {

    public Long id;
    public String role;
    public Long profileId;
    public String password;

    public UserResponseDto(Long id, String role, Long profileId, String password) {
        this.id = id;
        this.role = role;
        this.profileId = profileId;
        this.password = password;
    }
}
