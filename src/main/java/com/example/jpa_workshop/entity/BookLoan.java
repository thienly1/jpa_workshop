package com.example.jpa_workshop.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class BookLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loanId;

    private LocalDate loanDate;
    @Column(nullable = false)
    private LocalDate dueDate;
    @Column
    private boolean returned;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    private AppUser borrower;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    private Book book;

    public BookLoan() {
    }

    public BookLoan(LocalDate loanDate, LocalDate dueDate, boolean returned) {
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    public BookLoan(LocalDate loanDate, LocalDate dueDate, boolean returned, AppUser borrower, Book book) {
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returned = returned;
        this.borrower = borrower;
        this.book = book;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public AppUser getBorrower() {
        return borrower;
    }

    public void setBorrower(AppUser borrower) {
        this.borrower = borrower;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLoan bookLoan = (BookLoan) o;
        return getLoanId() == bookLoan.getLoanId() && isReturned() == bookLoan.isReturned() && Objects.equals(getLoanDate(), bookLoan.getLoanDate()) && Objects.equals(getDueDate(), bookLoan.getDueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoanId(), getLoanDate(), getDueDate(), isReturned());
    }

    @Override
    public String toString() {
        return "BookLoan{" +
                "loanId=" + loanId +
                ", loanDate=" + loanDate +
                ", dueDate=" + dueDate +
                ", returned=" + returned +
                ", borrower=" + borrower +
                ", book=" + book +
                '}';
    }
}
