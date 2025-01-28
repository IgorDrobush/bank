package com.bank.authorization.service;

import com.bank.authorization.dto.AuthRequestDto;
import com.bank.authorization.dto.UserRequestDto;
import com.bank.authorization.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    String createUser(UserRequestDto userRequestDto);

    String updateUser(Long id, UserRequestDto userRequestDto);

    void deleteUser(Long id);

    public ResponseEntity<String> login(AuthRequestDto authRequestDto);
}
