package com.example.e_commerce_database_management.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e_commerce_database_management.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}


