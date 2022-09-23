package com.example.jpa_workshop.dao;

import com.example.jpa_workshop.entity.AppUser;

import java.util.Collection;
import java.util.Optional;

public interface AppUserDao extends GenericCRUD<AppUser, Integer> {

    Optional<AppUser> findAppUserByUsername(String username);


}
