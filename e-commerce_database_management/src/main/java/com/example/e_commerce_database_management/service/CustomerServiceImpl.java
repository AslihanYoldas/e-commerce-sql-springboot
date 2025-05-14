package com.example.e_commerce_database_management.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.e_commerce_database_management.constants.CustomerHeaders;
import com.example.e_commerce_database_management.entity.Customer;
import com.example.e_commerce_database_management.exception.CsvParsingException;
import com.example.e_commerce_database_management.mapper.CustomerMapper;
import com.example.e_commerce_database_management.repository.CustomerRepository;



@Service
public class CustomerServiceImpl implements CustomerService {

private  CustomerRepository customerRepository;
private PasswordEncoder passwordEncoder;

public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
    this.customerRepository = customerRepository;
    this.passwordEncoder = passwordEncoder;
}


@Override
public int uploadCustomer(MultipartFile file) {
    Set<Customer> customers = parseCsv(file);
    customers.forEach(customer -> customer.setPassword(passwordEncoder.encode(customer.getPassword())));

    customerRepository.saveAll(customers);
    return customers.size();
    
}

private Set<Customer> parseCsv(MultipartFile file) {
    
    try (
        Reader reader =  new InputStreamReader(file.getInputStream()))
     {
         return CSVFormat.DEFAULT.builder()
            .setHeader(CustomerHeaders.getHeaders())
            .setSkipHeaderRecord(true)
            .setIgnoreEmptyLines(true)
            .setQuote('"')
            .setDelimiter(',')
            .setEscape('\\')
            .setTrim(true)
            .get()
            .parse(reader)
            .stream()
            .filter(record -> {
                boolean valid = record.size() >= CustomerHeaders.getHeaders().length;
                if (!valid) {
                    System.err.println("Skipping invalid record (too few columns): " + record);
                }
                return valid;
            })
            .map(CustomerMapper::mapToCustomer)
           
            .collect(Collectors.toSet());
                

    } catch (Exception e) {
        throw new CsvParsingException("Error parsing CSV file: " + e.getMessage());
    }
}



}



