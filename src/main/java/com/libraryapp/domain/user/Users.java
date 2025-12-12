package com.libraryapp.domain.user;

import com.libraryapp.domain.book.Book;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<LoanBookHistory> loanBookHistories = new ArrayList<>();

    public Users() {}

    public Users(String name, Integer age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수 입력입니다.");
        }
        this.name = name;
        this.age = age;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void loanBook(Book book) {
        this.loanBookHistories.add(new LoanBookHistory(this, book.getName(), false));
    }

    public void returnBook(String bookName) {
        LoanBookHistory target = this.loanBookHistories.stream()
                .filter(history -> history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow();
        target.doReturn();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
