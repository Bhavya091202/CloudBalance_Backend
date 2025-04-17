package com.example.CloudBalanceBackend.Service.Implimentation;

import com.example.CloudBalanceBackend.DTO.UserDTO;
import com.example.CloudBalanceBackend.Model.User;
import com.example.CloudBalanceBackend.Repository.UserRepository;
import com.example.CloudBalanceBackend.Service.UserService;
import com.example.CloudBalanceBackend.util.UserDtoEntityMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoEntityMapping userDtoEntityMapping;

    public UserServiceImpl(UserRepository userRepository, UserDtoEntityMapping userDtoEntityMapping) {
        this.userRepository = userRepository;
        this.userDtoEntityMapping = userDtoEntityMapping;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User entity = userDtoEntityMapping.map(userDTO);
        userRepository.save(entity);
        userDTO.setId(entity.getId());
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> entities = userRepository.findAll();
        return entities.stream()
                .map(userDtoEntityMapping::map)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User With id: " + id + " not found"));
        return userDtoEntityMapping.map(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User With id: " + id + " not found"));
        userDtoEntityMapping.updateEntityFromDto(userDTO, user);
        userRepository.save(user);
        return userDtoEntityMapping.map(user);
    }

    @Override
    public UserDTO updateUserPartially(Long id, UserDTO userDTO) {
        return null;
    }
}
