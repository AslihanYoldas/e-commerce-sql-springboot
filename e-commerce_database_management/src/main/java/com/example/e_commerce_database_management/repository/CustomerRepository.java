package com.example.e_commerce_database_management.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_commerce_database_management.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
