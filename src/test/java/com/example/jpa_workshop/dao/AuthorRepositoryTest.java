package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.Author;
import com.example.jpa_workshop.entity.Book;
import com.example.jpa_workshop.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    AuthorRepository authorRepository;

    Author testAuthor;

    public Set<Book> writtenBooks(){
       Set<Book> books= new HashSet<>();
       Book JavaF = new Book("ABC123", "Java Basic", 30);
       Book JavaA = new Book("ABC135", "Java advanced", 40);
       books.add(JavaA);
       books.add(JavaF);
       return books;
    }

    @BeforeEach
    void setUp() {
        Set<Book> persistedBooks = writtenBooks().stream().map(entityManager::persist).collect(Collectors.toSet());

        Set<Author> authorSet= new HashSet<>(Arrays.asList(new Author("ly", "Ta",persistedBooks),
                new Author("Mai", "Ta", persistedBooks)));
        Set<Author> persistedAuthors= authorSet.stream().map(entityManager::persist).collect(Collectors.toSet());
//        testAuthor= persistedAuthors.stream().findFirst().orElse(null); // still give the second object ???
        testAuthor= persistedAuthors.iterator().next(); // second object
    }

    @Test
    void findById() {
       Optional<Author> found = authorRepository.findById(testAuthor.getAuthorId());
        assertTrue(found.isPresent());
    }

    @Test
    void findAll() {
        assertEquals(2, authorRepository.findAll().size());
    }

    @Test
    void create() {
        Author author3 = new Author("Loan", "Le", null);
        authorRepository.save(author3);
        assertEquals(3, authorRepository.findAll().size());
    }

    @Test
    void delete() {
        authorRepository.deleteById(testAuthor.getAuthorId());
        assertEquals(1, authorRepository.findAll().size());
    }
}