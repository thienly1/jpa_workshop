package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.AppUser;
import com.example.jpa_workshop.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppUserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    AppUserRepository appUserRepository;
    AppUser testObject;
    @BeforeEach
    void init(){
        testObject = new AppUser("ly", "123456");
        entityManager.persist(testObject);
    }

    @Test
    void findById() {
        Optional<AppUser> found = appUserRepository.findById(testObject.getAppUserId());
        assertTrue(found.isPresent());
    }

    @Test
    void findAll() {
        Collection<AppUser> test = new ArrayList<>();
        test.add(testObject);
        assertEquals(appUserRepository.findAll(), test);
    }

    @Test
    void create() {
        AppUser appUser1= new AppUser("Roudabeh", "135246");
        appUserRepository.save(appUser1);
        assertEquals(2, appUser1.getAppUserId());
    }
    @Test
    void delete() {
        appUserRepository.deleteById(testObject.getAppUserId());
        Collection<AppUser> test = new ArrayList<>();
        assertEquals(test, appUserRepository.findAll());
    }
}