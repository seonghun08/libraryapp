package com.libraryapp.service;

import com.libraryapp.domain.user.UserRepository;
import com.libraryapp.domain.user.Users;
import com.libraryapp.dto.request.UserCreateRequest;
import com.libraryapp.dto.response.UserResponse;
import com.libraryapp.dto.request.UserUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long saveUser(UserCreateRequest request) {
        Users user = request.toEntity();
        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .toList();
    }

    @Transactional
    public void updateUserName(UserUpdateRequest request) {
        Users user = userRepository.findById(request.id())
                .orElseThrow(IllegalArgumentException::new);
        user.updateName(request.name());
    }

    @Transactional
    public void deleteUser(String name) {
        Users user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
    }

    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
