package com.example.e_commerce_database_management.mapper;


import java.time.LocalDate;

import org.apache.commons.csv.CSVRecord;

import com.example.e_commerce_database_management.dto.CustomerDto;
import com.example.e_commerce_database_management.entity.Customer;

public class CustomerMapper {


    public static CustomerDto mapToCustomerDto(Customer customer){
        return new CustomerDto(
            customer.getName(),
            customer.getPhoneNumber(),
            customer.getEmailAddress(),
            customer.getPassword(),
            customer.getRegistrationDate()
        );
    }

    public static Customer mapToCustomer(CustomerDto customerDto){
        return new Customer(
            null,
            customerDto.getName(),
            customerDto.getPhoneNumber(),
            customerDto.getEmailAddress(),
            customerDto.getPassword(),
            customerDto.getRegistrationDate()
        );
    }

    public static Customer mapToCustomer(CSVRecord record){
        return new Customer(
            null,
            record.get("customer_name"),
            record.get("customer_phone_number"),
            record.get("customer_email"),
            record.get("customer_password"),
            LocalDate.parse(record.get("customer_registration_date"))
            );
    }

}