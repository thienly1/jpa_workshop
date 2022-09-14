package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class BookDAOImpl implements BookDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Book findById(Integer id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Book> findAll() {
         return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Book create(Book book) {
        entityManager.persist(book);
        return book;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Book update(Book book) {
        return entityManager.merge(book);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(Integer id) {
        entityManager.remove(findById(id));

    }
}
