package com.bank.authorization.service;

import com.bank.authorization.model.entity.User;
import com.bank.authorization.model.dto.UserRequestDto;
import com.bank.authorization.model.dto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User userRequestDtoToUser(UserRequestDto userRequestDto);

    UserResponseDto userToUserResponseDto(User user);

    User userResponseDtoToUser(UserResponseDto userResponseDto);
}
