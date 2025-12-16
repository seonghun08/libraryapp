package com.libraryapp.domain.user.loanhistory

import com.libraryapp.domain.user.Users
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class UserLoanHistory(
    @ManyToOne
    @JoinColumn(name = "user_id")
    val users: Users,

    val bookName: String,

    @Enumerated(EnumType.STRING)
    var status: UserLoanStatus = UserLoanStatus.LOANED,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    val isReturn: Boolean
        get() = this.status == UserLoanStatus.RETURNED

    fun doReturn() {
        this.status = UserLoanStatus.RETURNED
    }

    companion object {
        fun fixture(
            users: Users,
            bookName: String = "kotlin",
            status: UserLoanStatus = UserLoanStatus.LOANED,
            id: Long? = null
        ): UserLoanHistory {
            return UserLoanHistory(
                users = users,
                bookName = bookName,
                status = status,
                id = id
            )
        }
    }
}