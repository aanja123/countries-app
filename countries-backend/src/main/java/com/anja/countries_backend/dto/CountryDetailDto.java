package com.anja.countries_backend.dto;

import java.util.List;

public record CountryDetailDto(
        String code,
        String name,
        String officialName,
        String capital,
        long population,
        String region,
        String subregion,
        Double area,
        String flagUrl,
        String flagAlt,
        List<String> languages,
        List<String> currencies,
        List<String> borders,
        List<String> timezones) {
}