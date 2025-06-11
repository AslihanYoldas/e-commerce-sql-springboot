package com.example.e_commerce_database_management.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.e_commerce_database_management.entity.Customer;

public interface CustomerService {

    int uploadCustomer(MultipartFile file);
    List<Customer> getAllCustomers();

}
