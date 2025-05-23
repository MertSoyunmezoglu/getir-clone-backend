package com.getir.clone.backend.controller;

import com.getir.clone.backend.dto.response.UserDTO;
import com.getir.clone.backend.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Kullanıcı işlemleri")
public class UserController {

    @GetMapping("/me")
    @Operation(summary = "Giriş yapan kullanıcıyı getir")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());

        return ResponseEntity.ok(dto);
    }
}
