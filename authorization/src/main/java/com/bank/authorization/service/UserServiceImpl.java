package com.bank.authorization.service;

import com.bank.authorization.dao.UserDao;
import com.bank.authorization.dto.AuthRequestDto;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.model.User;
import com.bank.authorization.dto.UserRequestDto;
import com.bank.authorization.dto.UserResponseDto;
import com.bank.authorization.security.jwt.JwtUtil;
import com.bank.authorization.validation.UserValidationException;
import com.bank.authorization.validation.UserValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(
            UserDao userDao,
            PasswordEncoder passwordEncoder,
            UserValidator userValidator, AuthenticationManager authenticationManager, JwtUtil jwtUtil
    ) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userDao.getAllUsers().stream().map(userMapper::userToUserResponseDto).toList();
    }

    @Override
    @Transactional
    public String createUser(UserRequestDto userRequestDto) {
        User user = userMapper.userRequestDtoToUser(userRequestDto);

        try {
            userValidator.validate(user);
        } catch (UserValidationException e) {
            return e.getMessage();
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User userToCreate = new User(user.getRole(), user.getProfileId(), encodedPassword);
        userDao.createUser(userToCreate);
        return "Пользователь создан";
    }

    @Override
    @Transactional
    public String updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userMapper.userRequestDtoToUser(userRequestDto);

        try {
            userValidator.validate(user);
        } catch (UserValidationException e) {
            return e.getMessage();
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User userToUpdate = userDao.findUserById(id);
        userDao.updateUser(user, userToUpdate);
        return "Пользователь обновлен";
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User userToDelete = userDao.findUserById(id);
        if (userToDelete != null) {
            userDao.deleteUser(userToDelete);
        }
    }

    @Override
    public ResponseEntity<String> login(AuthRequestDto authRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.username, authRequestDto.password)
            );

            String jwt = jwtUtil.generateToken(authentication.getName());
            return ResponseEntity.ok(jwt);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        }
    }
}
