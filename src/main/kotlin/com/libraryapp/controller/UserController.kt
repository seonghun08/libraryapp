package com.libraryapp.controller

import com.libraryapp.dto.request.UserCreateRequest
import com.libraryapp.dto.request.UserUpdateRequest
import com.libraryapp.dto.response.UserResponse
import com.libraryapp.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("/user")
    fun saveUser(@RequestBody request: UserCreateRequest): Long {
        return userService.saveUser(request)
    }

    @GetMapping("/user")
    fun getAllUsers(): List<UserResponse> {
        return userService.getAllUsers()
    }

    @PutMapping("/user")
    fun updateUserName(@RequestBody request: UserUpdateRequest) {
        userService.updateUserName(request)
    }

    @DeleteMapping("/user")
    fun deleteUser(@RequestParam name: String) {
        userService.deleteUser(name)
    }
}