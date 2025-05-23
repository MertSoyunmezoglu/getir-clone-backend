package com.getir.clone.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private UserDTO user;
    private String refreshToken;
}

