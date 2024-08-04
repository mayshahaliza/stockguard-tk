package com.apapeasy.stockguard.repository;

import com.apapeasy.stockguard.model.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDb extends JpaRepository<Category, Integer> {
    Optional<Category> findById(Integer id);
}