package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.Author;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class AuthorDaoImpl implements AuthorDao{

    @PersistenceContext
    EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public Author findById(Integer id) {
        return entityManager.find(Author.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Author> findAll() {
        return entityManager.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Author create(Author author) {
        entityManager.persist(author);
        return author;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Author update(Author author) {
        Author updated = entityManager.merge(author);
        return updated;
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(findById(id));
    }
}
