package com.libraryapp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.libraryapp.domain.book.BookRepository
import com.libraryapp.domain.book.BookType
import com.libraryapp.domain.user.loanhistory.LoanBookHistoryRepository
import com.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.libraryapp.dto.request.BookRequest
import com.libraryapp.dto.request.BookReturnRequest
import com.libraryapp.dto.request.LoanBookRequest
import com.libraryapp.dto.response.BookStatResponse
import com.libraryapp.service.BookService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@WebMvcTest(BookController::class)
class BookControllerHttpTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) {
    @MockitoBean
    private lateinit var bookService: BookService

    @MockitoBean
    private lateinit var loanBookHistoryRepository: LoanBookHistoryRepository

    @MockitoBean
    private lateinit var bookRepository: BookRepository

    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
    }

    @Test
    fun `GET - book loan - 대출중(LOANED) 권수 조회`() {
        // given
        given(bookService.countLoanedBook()).willReturn(3)

        // when & then
        mockMvc.get("/book/loan")
            .andExpect {
                status { isOk() }
                content { string("3") }
            }

        then(bookService).should().countLoanedBook()
    }

    @Test
    fun `POST - book - 책 저장`() {
        // given
        val request = BookRequest(name = "클린 코드", type = BookType.COMPUTER)
        given(bookService.saveBook(request)).willReturn(1L)

        // when & then
        mockMvc.post("/book") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            content { string("1") }
        }

        then(bookService).should().saveBook(request)
    }

    @Test
    fun `POST - book loan - 책 대출`() {
        // given
        val request = LoanBookRequest(userName = "홍길동", bookName = "클린 코드")

        // when & then
        mockMvc.post("/book/loan") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }

        then(bookService).should().loanBook(request)
    }

    @Test
    fun `PUT - book return - 책 반납`() {
        // given
        val request = BookReturnRequest(userName = "홍길동", bookName = "클린 코드")

        // when & then
        mockMvc.put("/book/return") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }

        then(bookService).should().returnBook(request)
    }

    @Test
    fun `GET - book stat - 통계 조회`() {
        // given
        val response = listOf(
            BookStatResponse(type = BookType.COMPUTER, count = 2),
            BookStatResponse(type = BookType.SCIENCE, count = 1),
        )
        given(bookService.getBookStatistics()).willReturn(response)

        // when & then
        mockMvc.get("/book/stat")
            .andExpect {
                status { isOk() }
                content { contentTypeCompatibleWith(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].type") { value("COMPUTER") }
                jsonPath("$[0].count") { value(2) }
            }

        then(bookService).should().getBookStatistics()
    }
}