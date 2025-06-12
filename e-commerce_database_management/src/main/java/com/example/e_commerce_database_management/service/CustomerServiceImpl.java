package com.example.e_commerce_database_management.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.e_commerce_database_management.constants.CustomerHeaders;
import com.example.e_commerce_database_management.entity.Customer;
import com.example.e_commerce_database_management.exception.CsvParsingException;
import com.example.e_commerce_database_management.mapper.CustomerMapper;
import com.example.e_commerce_database_management.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


private final CustomerRepository customerRepository;
private final PasswordEncoder passwordEncoder;



@Override
public int uploadCustomer(MultipartFile file) {
    Set<Customer> customers = parseCsv(file);
    
    //Encode Password
    customers.forEach(customer -> customer.setPassword(passwordEncoder.encode(customer.getPassword())));

    customerRepository.saveAll(customers);
    return customers.size();
    
}

private Set<Customer> parseCsv(MultipartFile file) {

    Set<Customer> Customers = new HashSet<>();
    
    
    
    try (
        Reader reader =  new InputStreamReader(file.getInputStream()))
     {
         CSVFormat format = CSVFormat.RFC4180.builder()
            .setHeader(CustomerHeaders.getHeaders())
            .setSkipHeaderRecord(true)
            .setIgnoreEmptyLines(true)
            .setTrim(true)
            .setQuote('"')
            .setEscape('"')
            .get();

            format.parse(reader)
            .stream()
            .forEach(record -> {
                int expected = CustomerHeaders.getHeaders().length;
                int actual = record.size();
                boolean valid = actual >= expected;

                if (valid) {

                    Customers.add(CustomerMapper.mapToCustomer(record));

                }
                else{
                    // if the record has fewer columns than expected
                    // fixAndMapRecords can parse that record and map it to customer
                    Customer fixedCustomer = fixAndMapRecords(record);
                    if(fixedCustomer != null){
                        Customers.add(fixedCustomer);
                    }
                }
                
            });
            
    } catch (Exception e) {
        throw new CsvParsingException("Error parsing CSV file: " + e.getMessage());
    }
    return Customers;
}
       

// Get the record value as a string and parse it correctly then return as a a CSVRecord
private static CSVRecord parseLine(String line) throws IOException {

        try(CSVParser parser = CSVFormat.RFC4180
                .builder()
                .setHeader(CustomerHeaders.getHeaders())
                .setQuote('"')
                .setEscape('"')
                .get()
                .parse(new StringReader(line))){
        List<CSVRecord> records = parser.getRecords();
        // Expect exactly one record here
        if (records.size() != 1) {
            throw new IOException("Expected single record but found: " + records.size());
        }
        return records.get(0);
    }
    }

// Get the value of that record as a string and parse that line with parseLine method map it to the customer
private static Customer fixAndMapRecords(CSVRecord badRecord) {
       Customer fixedCustomer= null;
            try {
                String rawLine = badRecord.get(0);
                CSVRecord fixedRecord = parseLine(rawLine) ;
                // Map fixedRecord fields to Customer
                fixedCustomer = CustomerMapper.mapToCustomer(fixedRecord);
                
                
            } catch (Exception e) {
                log.warn("Failed to fix record: {} due to {}", badRecord, e.getMessage());
            }
            return fixedCustomer;
        
            
    }


@Override
public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
}



}



