package com.example.CloudBalanceBackend.service;

import com.example.CloudBalanceBackend.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UserDTO userDTO);
    UserDTO updateUserPartially(Long id, UserDTO userDTO);
}
