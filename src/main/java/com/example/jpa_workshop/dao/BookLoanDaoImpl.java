package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.BookLoan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class BookLoanDaoImpl implements BookLoanDao{

    @PersistenceContext
    EntityManager entityManager;
    @Override
    public BookLoan findById(Integer id) {
        return entityManager.find(BookLoan.class, id);
    }

    @Override
    public Collection<BookLoan> findAll() {
        return entityManager.createQuery("select b from BookLoan b", BookLoan.class).getResultList();
    }

    @Override
    public BookLoan create(BookLoan bookLoan) {
        entityManager.persist(bookLoan);
        return bookLoan;
    }

    @Override
    public BookLoan update(BookLoan bookLoan) {
        entityManager.merge(bookLoan);
        return bookLoan;
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(findById(id));
    }
}
