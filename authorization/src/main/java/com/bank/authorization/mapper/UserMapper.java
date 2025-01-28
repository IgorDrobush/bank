package com.bank.authorization.mapper;

import com.bank.authorization.model.User;
import com.bank.authorization.dto.UserRequestDto;
import com.bank.authorization.dto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User userRequestDtoToUser(UserRequestDto userRequestDto);

    UserResponseDto userToUserResponseDto(User user);

    User userResponseDtoToUser(UserResponseDto userResponseDto);
}
