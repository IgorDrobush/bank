package com.bank.authorization.controller;

import com.bank.authorization.dto.AuthRequestDto;
import com.bank.authorization.model.User;
import com.bank.authorization.dto.UserRequestDto;
import com.bank.authorization.dto.UserResponseDto;
import com.bank.authorization.security.jwt.JwtUtil;
import com.bank.authorization.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User API", description = "Операции с пользователями")
public class AuthorizationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthorizationController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Operation(
            summary = "Получить список всех пользователей",
            description = "Возвращает полный список пользователей",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список пользователей получен"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping(value = "/get_all_users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Создать нового пользователя",
            description = "Добавляет нового пользователя в систему",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для создания пользователя",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
            }
    )
    @PostMapping(value = "/create")
    public String createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @Operation(
            summary = "Обновить пользователя",
            description = "Обновляет информацию о пользователе по ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлён"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @PutMapping(value = "/update/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(id, userRequestDto);
    }

    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя из системы по ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно удалён"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @DeleteMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "Пользователь удален";
    }

    @Operation(
            summary = "Показать роль пользователя",
            description =
                    "Показывает роль зарегистрированного пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Запрос удачный"),
            }
    )
    @GetMapping(value = "/role")
    public String getSuccessLogin(@AuthenticationPrincipal User user) {
        return "Успешно залогинились с ролью: " + user.getRole();
    }

    @Operation(
            summary = "Авторизация пользователя",
            description =
                    "Авторизует пользователя по логину и паролю",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Запрос удачный. Возвращается JWS токен"),
            }
    )
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDto authRequestDto) {
        return userService.login(authRequestDto);
    }
}
