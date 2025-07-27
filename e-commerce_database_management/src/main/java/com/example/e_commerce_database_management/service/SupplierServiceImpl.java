package com.example.e_commerce_database_management.service;

import com.example.e_commerce_database_management.entity.Supplier;
import com.example.e_commerce_database_management.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.e_commerce_database_management.constants.ConstantValues;

@RequiredArgsConstructor
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Supplier getSupplierForCategory(String categoryName) {

        // Getting the list of supplier names for that products category
        List<String> suppliers = ConstantValues.CATEGORY_SUPPLIER_MAP.getOrDefault((categoryName),List.of("Generic Supplier"));
        
        // Choosing a random supplier in that list
        String supplierName = suppliers.get(new Random().nextInt(suppliers.size()));

        //find that supplier in DB or create if not exist and return it
        return supplierRepository.findBySupplierName(supplierName).orElseGet(()->supplierRepository.save(new Supplier(null,supplierName)));

        
    }

}
