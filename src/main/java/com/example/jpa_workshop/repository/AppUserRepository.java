package com.example.jpa_workshop.repository;

import com.example.jpa_workshop.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    @Query("SELECT a FROM AppUser a where a.username= :username")
    Optional<AppUser> findAppUserByUsername(@Param("username") String username);

    List<AppUser> findAppUsersByUsernameContaining(String username);

    Optional<AppUser> findAppUserByUserDetailsEmail(String email);

    List<AppUser> findAppUsersByUserDetails_BirthDateBefore(LocalDate dateBefore);
    List<AppUser> findAppUsersByUserDetails_BirthDateAfter(LocalDate dateAfter);
    List<AppUser> findAppUsersByUserDetails_BirthDateBetween(LocalDate dateBefore, LocalDate dateAfter);

    /**
        Finding who is borrowing a specific book ny ISBN
     */
//    List<AppUser> findAppUsersByLoansBookIsbn(String ISBN); //same with the below method
    List<AppUser> findAppUsersByLoans_Book_Isbn(String ISBN);

    @Modifying
    @Query("UPDATE AppUser a set a.password= :pwd where a.username= :username")
    void updatePasswordByUserName(@Param("username") String username, @Param("pwd") String newPassword);

}
