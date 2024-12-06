package com.user.demo.service;

import java.util.List;
import java.util.UUID;

import com.user.demo.payload.request.UserRequest;
import com.user.demo.payload.response.AuthenticationResponse;
import com.user.demo.payload.response.UserResponse;

public interface UserService {
    UserResponse create (UserRequest userRequest);
    UserResponse getById (UUID id);
    List<UserResponse> getAll();
    UserResponse update (UUID id, UserRequest userRequest);
    UserResponse delete(UUID id);
    UserResponse getByUsername(String name);
    AuthenticationResponse generateToken(String username);
    void validateToken(String token);

}
