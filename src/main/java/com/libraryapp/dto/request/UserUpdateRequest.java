package com.libraryapp.dto.request;

public record UserUpdateRequest(
        long id,
        String name
) {
}
