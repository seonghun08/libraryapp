package com.libraryapp.dto.response;

import com.libraryapp.domain.user.Users;

public record UserResponse(
        long id,
        String name,
        Integer age
) {
    public UserResponse(Users user) {
        this(user.getId(), user.getName(), user.getAge());
    }
}
