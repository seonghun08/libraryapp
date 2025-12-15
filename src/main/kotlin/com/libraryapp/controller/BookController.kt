package com.libraryapp.controller

import com.libraryapp.dto.request.BookRequest
import com.libraryapp.dto.request.BookReturnRequest
import com.libraryapp.dto.request.LoanBookRequest
import com.libraryapp.service.BookService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val bookService: BookService
) {
    @PostMapping("/book")
    fun saveBook(@RequestBody request: BookRequest): Long {
        return bookService.saveBook(request)
    }

    @PostMapping("/book/loan")
    fun loanBook(@RequestBody request: LoanBookRequest) {
        bookService.loanBook(request)
    }

    @PutMapping("/book/return")
    fun returnBook(@RequestBody request: BookReturnRequest) {
        bookService.returnBook(request)
    }
}
