package com.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<Users, Long>, UserRepositoryCustom {

    @Query("SELECT u FROM Users u JOIN FETCH u.loanBookHistories WHERE u.name = :userName")
    fun findByNameWithLoanHistories(userName: String): Users?

    @Query("SELECT DISTINCT u FROM Users u LEFT JOIN FETCH u.loanBookHistories")
    fun findAllUsersWithLoanHistories(): List<Users>

    fun findByName(userName: String): Users?
}