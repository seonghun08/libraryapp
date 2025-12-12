package com.libraryapp.dto.request;

public record BookReturnRequest(
        String userName,
        String bookName
) {
}
