package com.libraryapp.domain.user;

import jakarta.persistence.*;

@Entity
public class LoanBookHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String bookName;

    private boolean isReturn;

    public LoanBookHistory() {}

    public LoanBookHistory(Users user, String bookName, boolean isReturn) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = isReturn;
    }

    public void doReturn() {
        this.isReturn = true;
    }

    public Long getId() {
        return id;
    }

    public Users getUser() {
        return user;
    }

    public String getBookName() {
        return bookName;
    }

    public boolean isReturn() {
        return isReturn;
    }
}
