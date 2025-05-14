package com.example.e_commerce_database_management.mapper;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.apache.commons.csv.CSVRecord;

import com.example.e_commerce_database_management.dto.CustomerDto;
import com.example.e_commerce_database_management.entity.Customer;
import com.example.e_commerce_database_management.exception.CsvParsingException;

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
        if (record.size() < 5) { // Assuming there are 5 expected columns
            System.err.println("RECORD=" +record);
             // Skip this record or handle it as needed
        }
        System.err.println(record.get("customer_name"));
        System.err.println(record.get("customer_phone_number"));
        System.err.println(record.get("customer_email"));
        System.err.println(record.get("customer_password"));
        

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH); // Specify locale if needed
        LocalDate registrationDate;
        String dateString = record.get("customer_registration_date"); // Get the date string
        try {
            registrationDate = LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new CsvParsingException("Invalid date format for registration date: " + dateString);
        }
        return new Customer(
            null,
            
            record.get("customer_name"),
            record.get("customer_phone_number"),
            record.get("customer_email"),
            record.get("customer_password"),
            registrationDate

            );

    }

}