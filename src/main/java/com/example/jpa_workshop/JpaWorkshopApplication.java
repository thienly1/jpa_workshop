package com.example.jpa_workshop;

import com.example.jpa_workshop.entity.*;
import com.example.jpa_workshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class JpaWorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaWorkshopApplication.class, args);
    }

    @Transactional
    @Component
    class MyCommandLineRunner implements CommandLineRunner{
        private final AppUserRepository appUserRepository;
        private final AuthorRepository authorRepository;
        private final BookRepository bookRepository;
        private final BookLoanRepository bookLoanRepository;
        private final DetailsRepository detailsRepository;

        @Autowired
        public MyCommandLineRunner(AppUserRepository appUserRepository, AuthorRepository authorRepository, BookRepository bookRepository, BookLoanRepository bookLoanRepository, DetailsRepository detailsRepository) {
            this.appUserRepository = appUserRepository;
            this.authorRepository = authorRepository;
            this.bookRepository = bookRepository;
            this.bookLoanRepository = bookLoanRepository;
            this.detailsRepository = detailsRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            AppUser ly= new AppUser("ly", "123456", new Details("ly@mail.com", "Ly Ta",
                    LocalDate.parse("1999-05-15")));
            AppUser mai = new AppUser("Mai", "135246", new Details("mai@mail.com", "Mai Ta",
                    LocalDate.parse("2005-09-23")));

            BookLoan bookLoan1 = new BookLoan(LocalDate.now(), LocalDate.now().plusDays(30), false);
            BookLoan bookLoan2= new BookLoan(LocalDate.now(), LocalDate.now().plusDays(40), false);
            bookLoanRepository.save(bookLoan1);
            bookLoanRepository.save(bookLoan2);
            appUserRepository.save(ly);
            appUserRepository.save(mai);

            ly.addBookLoan(bookLoan1);
            ly.addBookLoan(bookLoan2);

            Book javaCoreF = bookRepository.save(new Book("isbn123","Java Core : Fundamentals vol I 12 Edition",30));
            Book javaCoreA = bookRepository.save(new Book("isbn456","Java Core : Advanced vol II 12 Edition", 40));
            Book springB = bookRepository.save(new Book("isbn789","Spring Framework Basics", 50));

            Author Jame= authorRepository.save(new Author("Jame", "Thornson"));
            Author Michael = authorRepository.save(new Author("Michael", "John"));
            Author cay = authorRepository.save(new Author("Cay", "Horstmann"));
            javaCoreF.addAuthor(Jame);
            javaCoreF.addAuthor(Michael);
            javaCoreA.addAuthor(cay);
            javaCoreA.addAuthor(Jame);
            springB.addAuthor(Michael);
            springB.addAuthor(cay);

            appUserRepository.findAll().forEach(System.out::println);
            System.out.println( appUserRepository.findById(2));
            bookRepository.findAll().forEach(System.out::println);
            bookRepository.findById(1).orElseThrow(null);

            seedingData();

            Optional<AppUser> lyTa= appUserRepository.findAppUserByUsername("ly");
            lyTa.ifPresent(System.out::println);
        }

        private void seedingData() throws InterruptedException {

            AppUser martinChilling = appUserRepository.save(new AppUser("martinchilling", "ThisIsImportant",
                    new Details("martin.chilling@mail.com", "Martin Chilling", LocalDate.parse("1960-04-05"))));

            Book harryPotterDH = bookRepository.save(new Book("9780545139700", "Harry Potter and the Deathly Hallows", 14));

            Author j_k_Rowling = authorRepository.save(new Author("J.K", "Rowling"));

            //Adds Author and book relationship.
            j_k_Rowling.addBook(harryPotterDH);

                LocalDate dateOfBorrow = LocalDate.parse("2020-01-01");

                BookLoan martinBorrowsHarryPotter= bookLoanRepository.save(new BookLoan(dateOfBorrow,
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
