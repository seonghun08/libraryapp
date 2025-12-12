package com.libraryapp.dto.request;

import com.libraryapp.domain.book.Book;

public record BookRequest(
        String name
) {
    public Book toEntity() {
        return new Book(this.name);
    }
}
