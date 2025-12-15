package com.libraryapp.dto.request

import com.libraryapp.domain.user.Users

data class UserCreateRequest(
    val name: String,
    val age: Int?
) {
    fun toEntity(): Users {
        return Users(this.name, this.age)
    }
}
