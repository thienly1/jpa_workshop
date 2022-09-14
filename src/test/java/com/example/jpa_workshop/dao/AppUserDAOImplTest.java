package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@DirtiesContext
@Transactional
class AppUserDAOImplTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    AppUserDao appUserDao;
    AppUser appUser;
    @BeforeEach
    void init(){
        appUser= new AppUser("ly", "123456");
        entityManager.persist(appUser);
    }

    @Test
    void findById() {
        appUserDao.findById(appUser.getAppUserId());
        assertEquals(1, appUser.getAppUserId());
    }

    @Test
    void findAll() {
        Collection<AppUser> test = new ArrayList<>();
        test.add(appUser);
        assertEquals(appUserDao.findAll(), test);
    }

    @Test
    void create() {
        AppUser appUser1= new AppUser("Roudabeh", "135246");
        appUserDao.create(appUser1);
        assertEquals(2, appUser1.getAppUserId());
    }

    @Test
    void update() {
        appUser.setUsername("Ly1");
        appUserDao.update(appUser);
        assertEquals("Ly1", appUser.getUsername());
    }

    @Test
    void delete() {
        appUserDao.delete(1);
        Collection<AppUser> test = new ArrayList<>();
        assertEquals(test, appUserDao.findAll());
    }
}