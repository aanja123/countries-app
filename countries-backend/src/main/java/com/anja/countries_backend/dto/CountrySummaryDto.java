package com.anja.countries_backend.dto;

public record CountrySummaryDto(
        String code,
        String name,
        String capital,
        long population,
        String region,
        String flagUrl) {
}