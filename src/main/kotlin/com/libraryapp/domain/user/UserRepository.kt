package com.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<Users, Long> {

    @Query("""
        SELECT u
        FROM Users u
        JOIN FETCH u.loanBookHistories
        WHERE u.name = :userName
    """)
    fun findByNameWithLoanHistories(userName: String): Users?

    fun findByName(userName: String): Users?
}