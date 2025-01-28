package com.bank.authorization.mapper;

import com.bank.authorization.dto.UserRequestDto;
import com.bank.authorization.dto.UserResponseDto;
import com.bank.authorization.model.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-28T15:16:31+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User userRequestDtoToUser(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setRole( userRequestDto.role );
        user.setProfileId( userRequestDto.profileId );
        user.setPassword( userRequestDto.password );

        return user;
    }

    @Override
    public UserResponseDto userToUserResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String role = null;
        Long profileId = null;
        String password = null;

        id = user.getId();
        role = user.getRole();
        profileId = user.getProfileId();
        password = user.getPassword();

        UserResponseDto userResponseDto = new UserResponseDto( id, role, profileId, password );

        return userResponseDto;
    }

    @Override
    public User userResponseDtoToUser(UserResponseDto userResponseDto) {
        if ( userResponseDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userResponseDto.id );
        user.setRole( userResponseDto.role );
        user.setProfileId( userResponseDto.profileId );
        user.setPassword( userResponseDto.password );

        return user;
    }
}
