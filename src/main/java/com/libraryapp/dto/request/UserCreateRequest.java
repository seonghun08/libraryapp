package com.libraryapp.dto.request;

import com.libraryapp.domain.user.Users;

public record UserCreateRequest(
        String name,
        Integer age
) {
    public Users toEntity() {
        return new Users(this.name, this.age);
    }
}
