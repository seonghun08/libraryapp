package com.libraryapp.dto.response

import com.libraryapp.domain.user.Users

data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?
) {
    companion object {
        fun of(users: Users): UserResponse = UserResponse(
            id = users.id!!,
            name = users.name,
            age = users.age
        )
    }
}
