package com.getir.clone.backend.service.interfaces;

import com.getir.clone.backend.dto.request.RefreshTokenRequest;
import com.getir.clone.backend.dto.request.RegisterRequest;
import com.getir.clone.backend.dto.request.LoginRequest;
import com.getir.clone.backend.dto.request.UpdateUserRequest;
import com.getir.clone.backend.dto.response.AuthResponse;
import com.getir.clone.backend.dto.response.UserDTO;
import com.getir.clone.backend.entity.User;

public interface UserService {
    AuthResponse register(RegisterRequest request) ;
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    UserDTO updateUser(UpdateUserRequest request, User user);
    void logout(User user);
}
