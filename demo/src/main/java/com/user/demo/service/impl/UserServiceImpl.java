package com.user.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.demo.entity.User;
import com.user.demo.exception.CustomException;
import com.user.demo.payload.request.UserRequest;
import com.user.demo.payload.response.AuthenticationResponse;
import com.user.demo.payload.response.UserResponse;
import com.user.demo.repository.UserRepository;
import com.user.demo.service.JwtService;
import com.user.demo.service.UserService;

@Service 
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    @Override
    public UserResponse create(UserRequest userRequest) {
        User user = new User();
        mapRequestToEntity(userRequest, user);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword())); 
        User savedUser = userRepository.save(user);
        return mapEntityToResponse(savedUser);
    }

    @Override
    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", "NOT_FOUND"));
        return mapEntityToResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    @Override
    public UserResponse update(UUID id, UserRequest userRequest) {
        // Example implementation (not yet implemented fully)
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", "NOT_FOUND"));
        mapRequestToEntity(userRequest, user);
        User updatedUser = userRepository.save(user);
        return mapEntityToResponse(updatedUser);
    }

    @Override
    public UserResponse delete(UUID id) {
        // Example implementation (not yet implemented fully)
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", "NOT_FOUND"));
        userRepository.delete(user);
        return mapEntityToResponse(user);
    }

    @Override
    public UserResponse getByUsername(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new CustomException("User not found", "NOT_FOUND"));
        return mapEntityToResponse(user);
    }

    private UserResponse mapEntityToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }

    private void mapRequestToEntity(UserRequest userRequest, User user) {
        BeanUtils.copyProperties(userRequest, user);
    }

    public AuthenticationResponse generateToken(String username) {
        String token = jwtService.generateToken(username);
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new AuthenticationResponse(user.getId(), token);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
