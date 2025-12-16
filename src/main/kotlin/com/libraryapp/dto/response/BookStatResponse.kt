package com.libraryapp.dto.response

import com.libraryapp.domain.book.BookType

data class BookStatResponse(
    val type: BookType,
    val count: Long
)