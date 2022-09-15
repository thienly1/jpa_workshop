package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.BookLoan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class BookLoanDaoImpl implements BookLoanDao{

    @PersistenceContext
    EntityManager entityManager;
    @Transactional
    @Override
    public BookLoan findById(Integer id) {
        return entityManager.find(BookLoan.class, id);
    }

    @Transactional
    @Override
    public Collection<BookLoan> findAll() {
        return entityManager.createQuery("select b from BookLoan b", BookLoan.class).getResultList();
    }

    @Transactional
    @Override
    public BookLoan create(BookLoan bookLoan) {
        entityManager.persist(bookLoan);
        return bookLoan;
    }

    @Transactional
    @Override
    public BookLoan update(BookLoan bookLoan) {
        entityManager.merge(bookLoan);
        return bookLoan;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        entityManager.remove(findById(id));
    }
}
