package com.libraryapp.controller;

import com.libraryapp.dto.request.BookRequest;
import com.libraryapp.dto.request.BookReturnRequest;
import com.libraryapp.dto.request.LoanBookRequest;
import com.libraryapp.service.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book")
    public Long saveBook(@RequestBody BookRequest request) {
        return bookService.saveBook(request);
    }

    @PostMapping("/book/loan")
    public void loanBook(@RequestBody LoanBookRequest request) {
        bookService.loanBook(request);
    }

    @PutMapping("/book/return")
    public void returnBook(@RequestBody BookReturnRequest request) {
        bookService.returnBook(request);
    }
}
