package com.libraryapp.domain.user

import com.libraryapp.domain.book.Book
import com.libraryapp.domain.user.loanhistory.LoanBookHistory
import com.libraryapp.domain.user.loanhistory.UserLoanStatus
import jakarta.persistence.*

@Entity
class Users(
    @Column(nullable = false, unique = true)
    var name: String,

    val age: Int?,

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY)
    val loanBookHistories: MutableList<LoanBookHistory> = mutableListOf(),

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 필수 입력입니다.")
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        this.loanBookHistories.add(LoanBookHistory(this, book.name))
    }

    fun returnBook(bookName: String) {
        this.loanBookHistories
            .first { it.bookName == bookName }
            .doReturn()
    }
}