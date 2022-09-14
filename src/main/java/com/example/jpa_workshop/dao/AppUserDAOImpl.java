package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.AppUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class AppUserDAOImpl implements AppUserDao{

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public AppUser findById(Integer id) {
        AppUser appUser= entityManager.find(AppUser.class, id);
        return appUser;
    }

    @Transactional
    @Override
    public Collection<AppUser> findAll() {
        TypedQuery query = entityManager.createQuery("SELECT a FROM AppUser a", AppUser.class);
        return query.getResultList();
    }


    @Transactional
    @Override
    public AppUser create(AppUser appUser) {
        entityManager.persist(appUser);
        return appUser;
    }

    @Transactional
    @Override
    public AppUser update(AppUser appUser) {
        return entityManager.merge(appUser);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        if(findById(id)!=null){
            AppUser appUser = findById(id);
            entityManager.remove(appUser);
        }
    }
}
