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
class LoanBookHistory(
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: Users,

    val bookName: String,

    @Enumerated(EnumType.STRING)
    var status: UserLoanStatus = UserLoanStatus.LOANED,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    fun doReturn() {
        this.status = UserLoanStatus.RETURNED
    }

    companion object {
        fun fixture(
            user: Users,
            bookName: String = "kotlin",
            status: UserLoanStatus = UserLoanStatus.LOANED,
            id: Long? = null
        ): LoanBookHistory {
            return LoanBookHistory(
                user = user,
                bookName = bookName,
                status = status,
                id = id
            )
        }
    }
}