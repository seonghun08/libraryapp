package com.libraryapp.controller

import com.libraryapp.domain.user.loanhistory.LoanBookHistoryRepository
import com.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.libraryapp.dto.request.BookRequest
import com.libraryapp.dto.request.BookReturnRequest
import com.libraryapp.dto.request.LoanBookRequest
import com.libraryapp.dto.response.BookStatResponse
import com.libraryapp.service.BookService
import org.springframework.web.bind.annotation.*

@RestController
class BookController(
    private val bookService: BookService,
    private val loanBookHistoryRepository: LoanBookHistoryRepository
) {
    @PostMapping("/book")
    fun saveBook(@RequestBody request: BookRequest): Long {
        return bookService.saveBook(request)
    }

    @GetMapping("/book/loan")
    fun countLanedBook(): Int {
        return bookService.countLoanedBook()
    }

    @PostMapping("/book/loan")
    fun loanBook(@RequestBody request: LoanBookRequest) {
        bookService.loanBook(request)
    }

    @PutMapping("/book/return")
    fun returnBook(@RequestBody request: BookReturnRequest) {
        bookService.returnBook(request)
    }

    @GetMapping("/book/stat")
    fun getBookStatistics(): List<BookStatResponse> {
        return bookService.getBookStatistics()
    }
}
