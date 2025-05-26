package com.getir.clone.backend.service.impl;

import com.getir.clone.backend.dto.request.RefreshTokenRequest;
import com.getir.clone.backend.dto.request.RegisterRequest;
import com.getir.clone.backend.dto.request.LoginRequest;
import com.getir.clone.backend.dto.request.UpdateUserRequest;
import com.getir.clone.backend.dto.response.AuthResponse;
import com.getir.clone.backend.dto.response.UserDTO;
import com.getir.clone.backend.entity.User;
import com.getir.clone.backend.exceptions.*;
import com.getir.clone.backend.mapper.UserMapper;
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
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtProvider jwtProvider,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.userMapper = userMapper;
    }

    @Override
    public AuthResponse register(RegisterRequest request) throws EmailAlreadyUsedException {
        if (userRepository.existsByEmail(request.email()))
            throw new EmailAlreadyUsedException();

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());

        String accessToken = jwtProvider.generateToken(user.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refreshToken);
        User savedUser = userRepository.save(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                userMapper.toDto(savedUser)
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) throws UserNotFoundException {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new InvalidPasswordException();

        String accessToken = jwtProvider.generateToken(user.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                userMapper.toDto(user)
        );
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) throws BackendException {
        String refreshToken = request.refreshToken();

        if (!jwtProvider.validateToken(refreshToken))
            throw new InvalidRefreshTokenException();

        String email = jwtProvider.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (!refreshToken.equals(user.getRefreshToken())) throw new InvalidRefreshTokenException();

        String newAccessToken = jwtProvider.generateToken(user.getEmail());
        String newRefreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                userMapper.toDto(user)
        );
    }

    @Override
    public UserDTO updateUser(UpdateUserRequest request, User user) {
        userMapper.updateUserFromDto(request, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }
    @Override
    public void logout(User user) {
        user.setRefreshToken(null);
        userRepository.save(user);
    }
}
