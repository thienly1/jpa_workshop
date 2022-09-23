package com.example.jpa_workshop.repository;

import com.example.jpa_workshop.entity.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLoanRepository extends JpaRepository<BookLoan, Integer> {
}
