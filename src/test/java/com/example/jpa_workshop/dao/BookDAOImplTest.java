package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase
@DirtiesContext
@Transactional
class BookDAOImplTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    BookDAO bookDAO;
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

        Book found = bookDAO.findById(testObject.getBookId());
        assertNotNull(found);
        assertEquals(1, found.getBookId());
    }

    @Test
    void findAll() {
        Collection<Book> books = bookDAO.findAll();
        assertNotNull(books);
        assertEquals(2, books.size());
    }

    @Test
    void create() {
        Book newBook =  bookDAO.create(new Book("isbn4567", "Java Basic", 30));
        assertNotNull(newBook);
        assertEquals(3, newBook.getBookId());
    }

    @Test
    void update() {
        testObject.setIsbn("isbn000");
        Book updated = bookDAO.update(testObject);
        assertEquals("isbn000", updated.getIsbn());
    }

    @Test
    void delete() {
        bookDAO.delete(testObject.getBookId());
        assertEquals(1,bookDAO.findAll().size());
    }
}