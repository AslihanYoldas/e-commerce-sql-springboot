package com.example.e_commerce_database_management.entity;



import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customer")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    @Column(name = "customer_name", nullable = false)
    private String name;
    @Column(name = "customer_phone_number")
    private String phoneNumber;
    @Column(name = "customer_email", nullable = false, unique = true)
    private String emailAddress;
    @Column(name = "customer_password", nullable = false)
    private String password;
    @Column(name = "customer_registration_date")
    private LocalDate registrationDate;


    
}
