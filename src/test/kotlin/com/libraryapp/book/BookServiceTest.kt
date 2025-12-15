package com.libraryapp.book

import com.libraryapp.domain.book.Book
import com.libraryapp.domain.book.BookRepository
import com.libraryapp.domain.book.BookType
import com.libraryapp.domain.user.loanhistory.LoanBookHistory
import com.libraryapp.domain.user.loanhistory.LoanBookHistoryRepository
import com.libraryapp.domain.user.UserRepository
import com.libraryapp.domain.user.Users
import com.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.libraryapp.dto.request.BookRequest
import com.libraryapp.dto.request.BookReturnRequest
import com.libraryapp.dto.request.LoanBookRequest
import com.libraryapp.service.BookService
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val loanBookHistoryRepository: LoanBookHistoryRepository
) {
    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 정상 등록")
    fun saveBook() {
        // given
        val name = "kotlin"
        val request = BookRequest(name, BookType.COMPUTER)

        // when
        bookService.saveBook(request)

        // then
        val result = bookRepository.findAll()
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo(name)
        assertThat(result[0].type).isEqualTo(BookType.COMPUTER)
    }

    @Test
    @DisplayName("책 대출 정상")
    fun loanBook() {
        // given
        val userName = "hun"
        val bookName = "kotlin"
        userRepository.save(Users(userName, 20))
        bookRepository.save(Book.fixture(bookName))

        val request = LoanBookRequest(userName, bookName)

        // when
        bookService.loanBook(request)

        // then
        val user = userRepository.findByNameWithLoanHistories(userName) ?: throw IllegalArgumentException()
        assertThat(user.loanBookHistories).hasSize(1)
        assertThat(user.name).isEqualTo(userName)
        assertThat(user.loanBookHistories[0].bookName).isEqualTo(bookName)
        assertThat(user.loanBookHistories[0].status).isEqualTo(UserLoanStatus.LOANED)
    }

    @Test
    @DisplayName("이미 대출된 책이라면 대출 실패")
    fun loanBookFail() {
        // given
        val userName = "hun"
        val bookName = "kotlin"
        val user = userRepository.save(Users(userName, 20))
        bookRepository.save(Book.fixture(bookName))
        loanBookHistoryRepository.save(LoanBookHistory.fixture(user))

        val request = LoanBookRequest(userName, bookName)

        // when & then
        assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.apply { assertThat(this.message).isEqualTo("이미 대출된 책입니다.") }
    }

    @Test
    @DisplayName("책 반납 정상 동작")
    fun returnBook() {
        // given
        val userName = "hun"
        val bookName = "kotlin"
        val user = userRepository.save(Users(userName, 20))
        bookRepository.save(Book.fixture(bookName))
        loanBookHistoryRepository.save(LoanBookHistory.fixture(user))

        val request = BookReturnRequest(userName, bookName)

        // when
        bookService.returnBook(request)

        // then
        val result = loanBookHistoryRepository.findAll()
        assertThat(result).hasSize(1)
        assertThat(result[0].status).isEqualTo(UserLoanStatus.RETURNED)
    }
}