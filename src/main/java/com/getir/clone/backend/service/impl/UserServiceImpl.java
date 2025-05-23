package com.getir.clone.backend.service.impl;

import com.getir.clone.backend.dto.request.RefreshTokenRequest;
import com.getir.clone.backend.dto.request.RegisterRequest;
import com.getir.clone.backend.dto.request.LoginRequest;
import com.getir.clone.backend.dto.response.AuthResponse;
import com.getir.clone.backend.dto.response.UserDTO;
import com.getir.clone.backend.entity.User;
import com.getir.clone.backend.repository.UserRepository;
import com.getir.clone.backend.security.JwtProvider;
import com.getir.clone.backend.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Bu email ile kayıtlı kullanıcı zaten var.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());

        String accessToken = jwtProvider.generateToken(user.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refreshToken);
        User savedUser = userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setToken(accessToken);
        response.setRefreshToken(refreshToken);

        UserDTO dto = new UserDTO();
        dto.setId(savedUser.getId());
        dto.setEmail(savedUser.getEmail());
        dto.setFullName(savedUser.getFullName());
        response.setUser(dto);

        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Şifre yanlış.");
        }

        String accessToken = jwtProvider.generateToken(user.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setToken(accessToken);
        response.setRefreshToken(refreshToken);

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        response.setUser(dto);

        return response;
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh token geçersiz veya süresi dolmuş.");
        }

        String email = jwtProvider.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new RuntimeException("Refresh token eşleşmedi.");
        }

        String newAccessToken = jwtProvider.generateToken(user.getEmail());
        String newRefreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setToken(newAccessToken);
        response.setRefreshToken(newRefreshToken);

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        response.setUser(dto);

        return response;
    }
}
