package com.libraryapp.domain.book;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Book() {}

    public Book(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름을 필수 입력입니다.");
        }
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }
}
