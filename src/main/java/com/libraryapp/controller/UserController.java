package com.libraryapp.controller;

import com.libraryapp.dto.request.UserCreateRequest;
import com.libraryapp.dto.request.UserUpdateRequest;
import com.libraryapp.dto.response.UserResponse;
import com.libraryapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public Long saveUser(@RequestBody UserCreateRequest request) {
        return userService.saveUser(request);
    }

    @GetMapping("/user")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/user")
    public void updateUserName(@RequestBody UserUpdateRequest request) {
        userService.updateUserName(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }
}
