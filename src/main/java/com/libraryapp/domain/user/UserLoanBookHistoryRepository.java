package com.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoanBookHistoryRepository extends JpaRepository<LoanBookHistory, Long> {

    LoanBookHistory findByBookNameAndIsReturn(String bookName, boolean isReturn);

    boolean existsByBookNameAndIsReturn(String bookName, boolean isReturn);
}
