package com.anja.countries_backend.mapper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.anja.countries_backend.client.RestCountryResponse;
import com.anja.countries_backend.dto.CountryDetailDto;
import com.anja.countries_backend.dto.CountrySummaryDto;

@Component
public class CountryMapper {

    public CountrySummaryDto toSummary(RestCountryResponse response) {
        return new CountrySummaryDto(
                response.codes().alpha3(),
                response.names().common(),
                joinCapitals(response.capitals()),
                population(response),
                response.region(),
                flagPng(response));
    }

    public CountryDetailDto toDetail(RestCountryResponse response) {
        String flagAlt = response.flag() != null ? response.flag().description() : null;

        return new CountryDetailDto(
                response.codes().alpha3(),
                response.names().common(),
                response.names().official(),
                joinCapitals(response.capitals()),
                population(response),
                response.region(),
                response.subregion(),
                response.area() != null ? response.area().kilometers() : null,
                flagPng(response),
                flagAlt == null || flagAlt.isBlank() ? null : flagAlt,
                languages(response),
                currencies(response),
                response.borders() != null ? response.borders() : List.of(),
                response.timezones() != null ? response.timezones() : List.of());
    }

    private long population(RestCountryResponse response) {
        return response.population() != null ? response.population() : 0L;
    }

    private String joinCapitals(List<RestCountryResponse.Capital> capitals) {
        if (capitals == null || capitals.isEmpty()) {
            return null;
        }
        return capitals.stream()
                .map(RestCountryResponse.Capital::name)
                .filter(Objects::nonNull)
                .reduce((a, b) -> a + ", " + b)
                .orElse(null);
    }

    private String flagPng(RestCountryResponse response) {
        return response.flag() != null ? response.flag().urlPng() : null;
    }

    private List<String> languages(RestCountryResponse response) {
        if (response.languages() == null) {
            return List.of();
        }
        return response.languages().stream()
                .map(RestCountryResponse.Language::name)
                .filter(Objects::nonNull)
                .toList();
    }

    private List<String> currencies(RestCountryResponse response) {
        if (response.currencies() == null) {
            return List.of();
        }
        return response.currencies().stream()
                .map(c -> c.symbol() != null ? c.name() + " (" + c.symbol() + ")" : c.name())
                .toList();
    }
}