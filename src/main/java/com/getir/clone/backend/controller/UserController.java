package com.getir.clone.backend.controller;

import com.getir.clone.backend.dto.request.UpdateUserRequest;
import com.getir.clone.backend.dto.response.UserDTO;
import com.getir.clone.backend.entity.User;
import com.getir.clone.backend.mapper.UserMapper;
import com.getir.clone.backend.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Kullanıcı işlemleri")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    @Autowired
    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(summary = "Giriş yapan kullanıcıyı getir")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PutMapping("/update")
    @Operation(summary = "User update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UpdateUserRequest request,
                                              @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.updateUser(request, user));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal User user) {
        userService.logout(user);
        return ResponseEntity.ok("Çıkış başarılı.");
    }
}
