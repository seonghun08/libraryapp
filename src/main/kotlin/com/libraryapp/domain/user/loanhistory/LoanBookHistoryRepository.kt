package com.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository

interface LoanBookHistoryRepository : JpaRepository<UserLoanHistory, Long> {

    fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): UserLoanHistory?

    fun existsByBookNameAndStatus(bookName: String, status: UserLoanStatus): Boolean

    fun countByStatus(status: UserLoanStatus): Long
}