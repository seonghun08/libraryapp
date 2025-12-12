package com.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u JOIN FETCH u.loanBookHistories WHERE u.name = :name")
    Optional<Users> findByNameWithLoanHistories(String name);

    Optional<Users> findByName(String name);
}
