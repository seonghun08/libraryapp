package com.libraryapp.domain.user

interface UserRepositoryCustom {

    fun findAllWithHistories(): List<Users>
}