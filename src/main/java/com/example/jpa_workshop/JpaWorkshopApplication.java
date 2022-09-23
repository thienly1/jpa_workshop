package com.example.jpa_workshop;

import com.example.jpa_workshop.dao.AppUserDao;
import com.example.jpa_workshop.dao.AuthorDao;
import com.example.jpa_workshop.dao.BookDAO;
import com.example.jpa_workshop.dao.BookLoanDao;
import com.example.jpa_workshop.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootApplication
public class JpaWorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaWorkshopApplication.class, args);
    }

    @Component
    @Transactional
    class MyCommandLineRunner implements CommandLineRunner{
        private final AppUserDao appUserDao;
        private final AuthorDao authorDao;
        private final BookDAO bookDao;
        private final BookLoanDao bookLoanDao;


        @Autowired
        MyCommandLineRunner( AppUserDao appUserDao, AuthorDao authorDao, BookDAO bookDAO, BookLoanDao bookLoanDao) {
            this.appUserDao = appUserDao;
            this.authorDao = authorDao;
            this.bookDao = bookDAO;
            this.bookLoanDao = bookLoanDao;
        }
        @Override
        public void run(String... args) throws Exception {
            AppUser appUser1= new AppUser("ly", "123456", new Details("ly@mail.com", "Ly Ta",
                    LocalDate.parse("1999-05-15")));
            AppUser appUser2 = new AppUser("Mai", "135246", new Details("mai@mail.com", "Mai Ta",
                    LocalDate.parse("2005-09-23")));

            BookLoan bookLoan1 = new BookLoan(LocalDate.now(), LocalDate.now().plusDays(30), false);
            BookLoan bookLoan2= new BookLoan(LocalDate.now(), LocalDate.now().plusDays(40), false);
            bookLoanDao.create(bookLoan1);
            bookLoanDao.create(bookLoan2);
            appUserDao.create(appUser1);
            appUserDao.create(appUser2);

            appUser1.addBookLoan(bookLoan1);
            appUser1.addBookLoan(bookLoan2);

            Book javaCoreF = bookDao.create(new Book("isbn123","Java Core : Fundamentals vol I 12 Edition",30));
            Book javaCoreA = bookDao.create(new Book("isbn456","Java Core : Advanced vol II 12 Edition", 40));
            Book springB = bookDao.create(new Book("isbn789","Spring Framework Basics", 50));

            Author Jame= authorDao.create(new Author("Jame", "Thornson"));
            Author Michael = authorDao.create(new Author("Michael", "John"));
            Author cay = authorDao.create(new Author("Cay", "Horstmann"));
            javaCoreF.addAuthor(Jame);
            javaCoreF.addAuthor(Michael);
            javaCoreA.addAuthor(cay);
            javaCoreA.addAuthor(Jame);
            springB.addAuthor(Michael);
            springB.addAuthor(cay);

            appUserDao.findAll().forEach(System.out::println);
            System.out.println( appUserDao.findById(2));
            bookDao.findAll().forEach(System.out::println);
            bookDao.findById(1).getAuthors().forEach(System.out::println);


            seedingData();
        }

        private void seedingData() throws InterruptedException {

            AppUser martinChilling = appUserDao.create(new AppUser("martinchilling", "ThisIsImportant",
                    new Details("martin.chilling@mail.com", "Martin Chilling", LocalDate.parse("1960-04-05"))));

            Book harryPotterDH = bookDao.create(new Book("9780545139700", "Harry Potter and the Deathly Hallows", 14));

            Author j_k_Rowling = authorDao.create(new Author("J.K", "Rowling"));

            //Adds Author and book relationship.
            j_k_Rowling.addBook(harryPotterDH);

                LocalDate dateOfBorrow = LocalDate.parse("2020-01-01");

                BookLoan martinBorrowsHarryPotter= bookLoanDao.create(new BookLoan(dateOfBorrow,
                        dateOfBorrow.plusDays(
                        14),
                        false,
                        null,
                        harryPotterDH));
                martinChilling.addBookLoan(martinBorrowsHarryPotter);
                Thread.sleep(1000);
                martinChilling.getLoans().forEach(System.out::println);




        }
    }

}
