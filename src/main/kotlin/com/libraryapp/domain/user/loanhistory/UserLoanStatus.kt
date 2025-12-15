package com.libraryapp.domain.user.loanhistory

enum class UserLoanStatus(
    val stausName: String
) {
    RETURNED    ("반납"),
    LOANED      ("대출")
}