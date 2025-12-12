package com.libraryapp.service;

import com.libraryapp.domain.book.Book;
import com.libraryapp.domain.book.BookRepository;
import com.libraryapp.domain.user.UserLoanBookHistoryRepository;
import com.libraryapp.domain.user.UserRepository;
import com.libraryapp.domain.user.Users;
import com.libraryapp.dto.request.BookRequest;
import com.libraryapp.dto.request.BookReturnRequest;
import com.libraryapp.dto.request.LoanBookRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserLoanBookHistoryRepository userLoanBookHistoryRepository;

    public BookService(
            BookRepository bookRepository,
            UserRepository userRepository,
            UserLoanBookHistoryRepository userLoanBookHistoryRepository
    ) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userLoanBookHistoryRepository = userLoanBookHistoryRepository;
    }

    @Transactional
    public Long saveBook(BookRequest request) {
        Book book = request.toEntity();
        return bookRepository.save(book).getId();
    }

    @Transactional
    public void loanBook(LoanBookRequest request) {
        Book book = bookRepository.findByName(request.bookName())
                .orElseThrow(IllegalArgumentException::new);

        if (userLoanBookHistoryRepository.existsByBookNameAndIsReturn(request.bookName(), false)) {
            throw new IllegalArgumentException("이미 대출된 책입니다.");
        }

        Users user = userRepository.findByName(request.userName())
                .orElseThrow(IllegalArgumentException::new);
        user.loanBook(book);
    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        if (!bookRepository.existsBookByName(request.bookName())) {
            throw new IllegalArgumentException("존재하지 않는 책 이름입니다.");
        }

        Users user = userRepository.findByNameWithLoanHistories(request.userName())
                .orElseThrow(IllegalArgumentException::new);
        user.returnBook(request.bookName());
    }
}
