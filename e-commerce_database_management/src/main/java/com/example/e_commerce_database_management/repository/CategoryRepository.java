package com.example.e_commerce_database_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e_commerce_database_management.entity.Category;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    // To find if category already exists on database
    // Optional helps handle the case where nothing is found
    Optional<Category>findByCategoryName(String categoryName);

}
