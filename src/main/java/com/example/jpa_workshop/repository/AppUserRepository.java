package com.example.jpa_workshop.repository;

import com.example.jpa_workshop.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    @Query("SELECT a FROM AppUser a where a.username= :username")
    Optional<AppUser> findAppUserByUsername(@Param("username") String username);
}
