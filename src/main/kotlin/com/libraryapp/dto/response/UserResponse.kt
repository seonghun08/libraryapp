package com.libraryapp.dto.response

import com.libraryapp.domain.user.Users

data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?
) {
    companion object {
        fun of(user: Users): UserResponse = UserResponse(
            id = user.id!!,
            name = user.name,
            age = user.age
        )
    }
}
