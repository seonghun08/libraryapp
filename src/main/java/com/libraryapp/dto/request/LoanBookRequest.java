package com.libraryapp.dto.request;

public record LoanBookRequest(
        String userName,
        String bookName
) {
}
