package com.example.e_commerce_database_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CsvParsingException extends RuntimeException{

    public CsvParsingException(String message){
        super(message);
    }


}