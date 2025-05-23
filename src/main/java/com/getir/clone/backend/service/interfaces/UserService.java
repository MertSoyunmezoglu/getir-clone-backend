package com.getir.clone.backend.service.interfaces;

import com.getir.clone.backend.dto.request.RefreshTokenRequest;
import com.getir.clone.backend.dto.request.RegisterRequest;
import com.getir.clone.backend.dto.request.LoginRequest;
import com.getir.clone.backend.dto.response.AuthResponse;

public interface UserService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
}
