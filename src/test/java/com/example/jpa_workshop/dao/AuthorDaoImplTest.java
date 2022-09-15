package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.Author;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase
@DirtiesContext
@Transactional
class AuthorDaoImplTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    AuthorDao authorDao;

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
        Author found = authorDao.findById(testAuthor.getAuthorId());
        assertEquals(2,found.getAuthorId());
    }

    @Test
    void findAll() {
        assertEquals(2, authorDao.findAll().size());
    }

    @Test
    void create() {
        Author author3 = new Author("Loan", "Le", null);
        authorDao.create(author3);
        assertEquals(3, authorDao.findAll().size());
    }

    @Test
    void update() {
        testAuthor.setFirstName("Mai1");
        Author updated = authorDao.update(testAuthor);
        assertEquals("Mai1", updated.getFirstName());
    }

    @Test
    void delete() {
        authorDao.delete(testAuthor.getAuthorId());
        assertEquals(1, authorDao.findAll().size());
    }
}