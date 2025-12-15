package com.libraryapp.domain.book

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
class Book(
    @Column(nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    val type: BookType,

    // pk는 생성자에 가장 아래로 배치하는 것이 관례이다.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름을 필수 입력입니다.")
        }
    }

    // companion object 또한 아래에 배치하는 것이 관례이다.
    companion object {
        fun fixture(
            name: String = "kotlin",
            type: BookType = BookType.COMPUTER,
            id: Long? = null
        ): Book {
            return Book(
                name = name,
                type = type,
                id = id
            )
        }
    }
}