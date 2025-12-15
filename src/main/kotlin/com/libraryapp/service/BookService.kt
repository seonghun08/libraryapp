package com.libraryapp.service

import com.libraryapp.domain.book.BookRepository
import com.libraryapp.domain.user.loanhistory.LoanBookHistoryRepository
import com.libraryapp.domain.user.UserRepository
import com.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.libraryapp.dto.request.BookRequest
import com.libraryapp.dto.request.BookReturnRequest
import com.libraryapp.dto.request.LoanBookRequest
import com.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val loanBookHistoryRepository: LoanBookHistoryRepository
) {
    @Transactional
    fun saveBook(request: BookRequest): Long {
        return bookRepository.save(request.toEntity()).id!!
    }

    @Transactional
    fun loanBook(request: LoanBookRequest) {
        val book = bookRepository.findByName(request.bookName) ?: fail()

        if (loanBookHistoryRepository.existsByBookNameAndStatus(request.bookName, UserLoanStatus.LOANED)) {
            throw IllegalArgumentException("이미 대출된 책입니다.")
        }

        val user = userRepository.findByName(request.userName) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest) {
        if (!bookRepository.existsBookByName(request.bookName)) {
            throw IllegalArgumentException("존재하지 않은 책 이름입니다.")
        }

        val user = userRepository.findByNameWithLoanHistories(request.userName) ?: fail()
        user.returnBook(request.bookName)
    }
}