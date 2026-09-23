package com.anja.countries_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anja.countries_backend.dto.CountryDetailDto;
import com.anja.countries_backend.dto.CountrySummaryDto;
import com.anja.countries_backend.service.CountryService;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<CountrySummaryDto> getCountries(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String sort) {
        return countryService.getCountries(name, region, sort);
    }

    @GetMapping("/{code}")
    public CountryDetailDto getCountry(@PathVariable String code) {
        return countryService.getCountry(code);
    }
}