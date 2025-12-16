package com.libraryapp.domain.user

import com.libraryapp.domain.user.QUsers.users
import com.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.querydsl.jpa.impl.JPAQueryFactory

class UserRepositoryCustomImpl(
    private val query: JPAQueryFactory
): UserRepositoryCustom {

    override fun findAllWithHistories(): List<Users> {
        return query
            .selectFrom(users)
            .leftJoin(userLoanHistory)
                .on(userLoanHistory.users.id.eq(users.id))
            .fetch()
    }
}