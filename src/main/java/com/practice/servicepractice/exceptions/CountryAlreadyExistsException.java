package com.practice.servicepractice.exceptions;

public class CountryAlreadyExistsException extends RuntimeException{
    public CountryAlreadyExistsException(String name) {
        super("Country with name '" + name + "' already exists");
    }
}
