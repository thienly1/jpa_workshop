package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.AppUser;
import com.example.jpa_workshop.entity.Book;
import com.example.jpa_workshop.entity.BookLoan;
import com.example.jpa_workshop.entity.Details;
import com.example.jpa_workshop.repository.BookLoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookLoanRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    BookLoanRepository bookLoanRepository;

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
        Optional<BookLoan> found = bookLoanRepository.findById(testObject.getLoanId());
        assertNotNull(found);
        assertTrue(found.isPresent());
    }

    @Test
    void findAll() {
        assertEquals(2, bookLoanRepository.findAll().size());
    }

    @Test
    void create() {
        BookLoan bookLoan= new BookLoan(LocalDate.now(), LocalDate.now().plusDays(40), false, null, null);
        bookLoanRepository.save(bookLoan);
        assertEquals(3, bookLoanRepository.findAll().size());
    }

    @Test
    void delete() {
        bookLoanRepository.deleteById(testObject.getLoanId());
        assertEquals(1, bookLoanRepository.findAll().size());
    }
}