package com.example.e_commerce_database_management.dto;


import java.time.LocalDate;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

 
    @NotBlank(message = "Customer name is required")
    private String name;

    
    private String phoneNumber;

   
    @NotBlank(message = "email is required")
    @Email(message = "Invalid email format")
    private String emailAddress;

    
    @NotBlank(message = "email is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    
    private LocalDate registrationDate;



    
}