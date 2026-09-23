package com.anja.countries_backend.exception;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(String code) {
        super("Country with code '" + code + "' was not found.");
    }
}