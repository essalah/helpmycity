package com.helpmycity.repository;

import com.helpmycity.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    Optional<Category> findById(Long aLong);

    @Override
    List<Category> findAll();

    @Override
    void deleteById(Long id);

}
