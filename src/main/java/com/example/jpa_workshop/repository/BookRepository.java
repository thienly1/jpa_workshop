package com.example.jpa_workshop.repository;

import com.example.jpa_workshop.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
