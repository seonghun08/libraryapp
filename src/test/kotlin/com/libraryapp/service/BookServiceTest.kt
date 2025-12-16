package com.libraryapp.service

import com.libraryapp.domain.book.Book
import com.libraryapp.domain.book.BookRepository
import com.libraryapp.domain.book.BookType.COMPUTER
import com.libraryapp.domain.book.BookType.ECONOMY
import com.libraryapp.domain.book.BookType.LANGUAGE
import com.libraryapp.domain.book.BookType.SCIENCE
import com.libraryapp.domain.book.BookType.SOCIETY
import com.libraryapp.domain.user.Users
import com.libraryapp.domain.user.UserRepository
import com.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.libraryapp.domain.user.loanhistory.LoanBookHistoryRepository
import com.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.libraryapp.dto.request.BookRequest
import com.libraryapp.dto.request.BookReturnRequest
import com.libraryapp.dto.request.LoanBookRequest
import org.assertj.core.api.AssertionsForClassTypes
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
    private val loanBookHistoryRepository: LoanBookHistoryRepository,
) {
    @AfterEach
    fun clean() {
        println("========= CLEAN =========")
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 정상 등록")
    fun saveBook() {
        // given
        val name = "kotlin"
        val request = BookRequest(name, COMPUTER)

        // when
        bookService.saveBook(request)

        // then
        val result = bookRepository.findAll()
        assertThat(result).hasSize(1)
        AssertionsForClassTypes.assertThat(result[0].name).isEqualTo(name)
        assertThat(result[0].type).isEqualTo(COMPUTER)
    }

    @Test
    @DisplayName("책 대출 정상")
    fun loanBook() {
        // given
        val userName = "hun"
        val bookName = "kotlin"
        userRepository.save(Users(userName, 20))
        bookRepository.save(Book.Companion.fixture(bookName))

        val request = LoanBookRequest(userName, bookName)

        // when
        bookService.loanBook(request)

        // then
        val user = userRepository.findByNameWithLoanHistories(userName) ?: throw IllegalArgumentException()
        assertThat(user.loanBookHistories).hasSize(1)
        AssertionsForClassTypes.assertThat(user.name).isEqualTo(userName)
        AssertionsForClassTypes.assertThat(user.loanBookHistories[0].bookName).isEqualTo(bookName)
        assertThat(user.loanBookHistories[0].status).isEqualTo(UserLoanStatus.LOANED)
    }

    @Test
    @DisplayName("이미 대출된 책이라면 대출 실패")
    fun loanBookFail() {
        // given
        val userName = "hun"
        val bookName = "kotlin"
        val users = userRepository.save(Users(userName, 20))
        bookRepository.save(Book.Companion.fixture(bookName))
        loanBookHistoryRepository.save(UserLoanHistory.Companion.fixture(users))

        val request = LoanBookRequest(userName, bookName)

        // when & then
        assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.apply { AssertionsForClassTypes.assertThat(this.message).isEqualTo("이미 대출된 책입니다.") }
    }

    @Test
    @DisplayName("책 반납 정상 동작")
    fun returnBook() {
        // given
        val userName = "hun"
        val bookName = "kotlin"
        val users = userRepository.save(Users(userName, 20))
        bookRepository.save(Book.Companion.fixture(bookName))
        loanBookHistoryRepository.save(UserLoanHistory.Companion.fixture(users))

        val request = BookReturnRequest(userName, bookName)

        // when
        bookService.returnBook(request)

        // then
        val result = loanBookHistoryRepository.findAll()
        assertThat(result).hasSize(1)
        assertThat(result[0].status).isEqualTo(UserLoanStatus.RETURNED)
    }

    @Test
    @DisplayName("책 대여 권수를 정상 확인")
    fun countLoanedBook() {
        // given
        val users = userRepository.save(Users("hun", null))
        loanBookHistoryRepository.saveAll(listOf(
                UserLoanHistory.fixture(users, "book1", UserLoanStatus.RETURNED),
                UserLoanHistory.fixture(users, "book2"),
                UserLoanHistory.fixture(users, "book3")
        ))

        // when
        val count = bookService.countLoanedBook()

        // then
        assertThat(count).isEqualTo(2)
    }

    @Test
    @DisplayName("분야 별 권수를 확인")
    fun getBookStatistics() {
        // given
        bookRepository.saveAll(listOf(
            Book.fixture("book1", COMPUTER),
            Book.fixture("book2", COMPUTER),
            Book.fixture("book3", LANGUAGE),
            Book.fixture("book5", ECONOMY)
        ))

        // when
        val results = bookService.getBookStatistics()

        // then
        assertThat(results).hasSize(3)
        results.forEach { result ->
            val expectedCount = when(result.type) {
                COMPUTER -> 2L
                LANGUAGE, ECONOMY -> 1L
                SOCIETY, SCIENCE -> 0L
            }
            assertThat(result.count).isEqualTo(expectedCount)
        }
    }
}