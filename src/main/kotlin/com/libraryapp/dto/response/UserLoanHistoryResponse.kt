package com.libraryapp.dto.response

import com.libraryapp.domain.user.Users
import com.libraryapp.domain.user.loanhistory.UserLoanHistory

data class UserLoanHistoryResponse(
    val name: String,
    val books: List<BookHistoryResponse>
) {
    companion object {
        fun of(users: Users): UserLoanHistoryResponse {
            return UserLoanHistoryResponse(
                name = users.name,
                books = users.loanBookHistories.map(BookHistoryResponse::of)
            )
        }
    }
}

data class BookHistoryResponse(
    val name: String,
    val isReturn: Boolean
) {
    companion object {
        fun of(history: UserLoanHistory): BookHistoryResponse {
            return BookHistoryResponse(
                name = history.bookName,
                isReturn = history.isReturn
            )
        }
    }
}
