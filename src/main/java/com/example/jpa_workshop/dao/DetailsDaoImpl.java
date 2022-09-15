package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.AppUser;
import com.example.jpa_workshop.entity.Details;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class DetailsDaoImpl implements DetailsDao{

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public Details findById(Integer id) {
        return entityManager.find(Details.class, id);
    }

    @Transactional
    @Override
    public Collection<Details> findAll() {
        TypedQuery<Details> query = entityManager.createQuery("select d from Details d", Details.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Details create(Details details) {
        if(details==null) throw new IllegalArgumentException("details is null, invalid value");
        entityManager.persist(details);
        return details ;
    }

    @Transactional
    @Override
    public Details update(Details details) {
        if(details==null) throw new IllegalArgumentException("details is null, invalid value");
        entityManager.merge(details);
        return details;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Details details= findById(id);
        if(details==null) throw new IllegalArgumentException("not found");
        entityManager.remove(details);
    }
}
