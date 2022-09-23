package com.example.jpa_workshop.repository;

import com.example.jpa_workshop.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
