package com.example.jpa_workshop.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appUserId;
    @Column(length = 100, nullable = false, unique = true)
    private String username;
    @Column(length = 100, nullable = false)
    private String password;
    @CreationTimestamp
    private LocalDate regDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "details_id", referencedColumnName = "detailsId")
    private Details userDetails;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.PERSIST)
    private List<BookLoan> loans;

    public AppUser() {
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AppUser(String username, String password,Details userDetails) {
        this.username = username;
        this.password = password;
        setRegDate(LocalDate.now());
        this.userDetails = userDetails;
    }
    public void addBookLoan(BookLoan bookLoan){
        if(loans==null) loans= new ArrayList<>();
        loans.add(bookLoan);
        bookLoan.setBorrower(this);
    }
    public void removeBookLoan(BookLoan bookLoan){
        if(loans!= null){
            if(loans.contains(bookLoan)){
                bookLoan.setBorrower(null);
                loans.remove(bookLoan);
            }
        }
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public Details getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Details userDetails) {
        this.userDetails = userDetails;
    }

    public List<BookLoan> getLoans() {
        return loans;
    }

    public void setLoans(List<BookLoan> loans) {
        this.loans = loans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return getAppUserId() == appUser.getAppUserId() && Objects.equals(getUsername(), appUser.getUsername()) && Objects.equals(getPassword(), appUser.getPassword()) && Objects.equals(getRegDate(), appUser.getRegDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppUserId(), getUsername(), getPassword(), getRegDate());
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "appUserId=" + appUserId +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", regDate=" + regDate +
                ", userDetails=" + userDetails +
                '}';
    }
}
