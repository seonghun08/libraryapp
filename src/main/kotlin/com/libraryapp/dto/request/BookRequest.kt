package com.libraryapp.dto.request

import com.libraryapp.domain.book.Book
import com.libraryapp.domain.book.BookType

data class BookRequest(
    val name: String,
    val type: BookType
) {
    fun toEntity(): Book {
        return Book(this.name, this.type)
    }
}
