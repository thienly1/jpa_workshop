package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.AppUser;
import com.example.jpa_workshop.entity.Book;
import com.example.jpa_workshop.entity.BookLoan;
import com.example.jpa_workshop.entity.Details;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase
@DirtiesContext
@Transactional
class BookLoanDaoImplTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    BookLoanDao bookLoanDao;

    BookLoan testObject;

    public List<AppUser> appUsers(){
        return Arrays.asList(
                new AppUser("simonelbrink","ThisIsASecretPassword",
                        new Details("simon@lexicon.se","Simon Elbrink", LocalDate.parse("1997-03-18"))),
                new AppUser("ulfbengtsson","ThisIsAnOtherPassword",
                        new Details("ulf@lexicon.se","Ulf Bengtsson", LocalDate.parse("1982-08-25")))
        );
    }
    public List<Book> books(){
        return Arrays.asList(
                new Book("34348934fk","Java Core",15),
                new Book("34348934fk","Java Core Advanced",15)
        );
    }

    @BeforeEach
    void setUp() {
        List<Book> persistedBook = books().stream().map(entityManager::persist).collect(Collectors.toList());
        List<AppUser> persistedAppUser = appUsers().stream().map(entityManager::persist).collect(Collectors.toList());
        List<BookLoan> bookLoans = Arrays.asList(new BookLoan(LocalDate.now(), LocalDate.now().plusDays(60),
                        false,
                        persistedAppUser.get(0),
                        persistedBook.get(0)),
                new BookLoan(LocalDate.now(), LocalDate.now().plusDays(30),
                        false,
                        persistedAppUser.get(0),
                        persistedBook.get(1)));
        List<BookLoan> persistedBookLoan = bookLoans.stream().map(entityManager::persist).collect(Collectors.toList());
        testObject= persistedBookLoan.get(0);
    }

    @Test
    void findById() {
        BookLoan found = bookLoanDao.findById(testObject.getLoanId());
        assertNotNull(found);
        assertEquals(1, found.getLoanId());
    }

    @Test
    void findAll() {
        assertEquals(2, bookLoanDao.findAll().size());
    }

    @Test
    void create() {
        BookLoan bookLoan= new BookLoan(LocalDate.now(), LocalDate.now().plusDays(40), false, null, null);
        bookLoanDao.create(bookLoan);
        assertEquals(3, bookLoanDao.findAll().size());
    }

    @Test
    void update() {
        testObject.setReturned(true);
        BookLoan updated = bookLoanDao.update(testObject);
        assertTrue(updated.isReturned());
    }

    @Test
    void delete() {
        bookLoanDao.delete(testObject.getLoanId());
        assertEquals(1, bookLoanDao.findAll().size());
    }
}