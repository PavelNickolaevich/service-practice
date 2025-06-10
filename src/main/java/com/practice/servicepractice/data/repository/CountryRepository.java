package com.practice.servicepractice.data.repository;

import com.practice.servicepractice.data.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
    boolean existsByName(String name);

}
