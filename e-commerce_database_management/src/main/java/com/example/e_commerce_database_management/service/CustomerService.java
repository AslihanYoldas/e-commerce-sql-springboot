package com.example.e_commerce_database_management.service;


import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {

    int uploadCustomer(MultipartFile file);

}
