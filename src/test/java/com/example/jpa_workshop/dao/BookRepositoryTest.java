package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.Book;
import com.example.jpa_workshop.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BookRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    BookRepository bookRepository;
    Book testObject;

    public List<Book> books(){
        return Arrays.asList(new Book("isbn123", "Java Fundermental", 90),
        new Book("isbn456","Java Advanced", 90));
    }

    @BeforeEach
    void setUp() {
        List<Book> persistedBooks =
                books().stream()
                        .map(entityManager::persist)
                        .collect(Collectors.toList());
        testObject = persistedBooks.get(0);
    }

    @Test
    void findById() {

        Optional<Book> found = bookRepository.findById(testObject.getBookId());
        assertNotNull(found);
        assertTrue(found.isPresent());
    }

    @Test
    void findAll() {
        Collection<Book> books = bookRepository.findAll();
        assertNotNull(books);
        assertEquals(2, books.size());
    }

    @Test
    void create() {
        Book newBook =  bookRepository.save(new Book("isbn4567", "Java Basic", 30));
        assertNotNull(newBook);
        assertEquals(3, newBook.getBookId());
    }

    @Test
    void delete() {
        bookRepository.deleteById(testObject.getBookId());
        assertEquals(1, bookRepository.findAll().size());
    }
}