package com.example.e_commerce_database_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.e_commerce_database_management.service.CustomerService;

import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/upload")
    public ResponseEntity<Integer> uploadCustomer(
        @RequestPart(value = "file", required = false) MultipartFile file
    ){
        return ResponseEntity.ok(customerService.uploadCustomer(file));
    }
    

}
