package com.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository

interface LoanBookHistoryRepository : JpaRepository<LoanBookHistory, Long> {

    fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): LoanBookHistory?

    fun existsByBookNameAndStatus(bookName: String, status: UserLoanStatus): Boolean
}