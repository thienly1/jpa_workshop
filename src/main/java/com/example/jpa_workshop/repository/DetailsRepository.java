package com.example.jpa_workshop.repository;

import com.example.jpa_workshop.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<Details, Integer> {
}
