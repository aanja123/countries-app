package com.anja.countries_backend.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.anja.countries_backend.client.RestCountriesClient;
import com.anja.countries_backend.client.RestCountryResponse;
import com.anja.countries_backend.dto.CountryDetailDto;
import com.anja.countries_backend.dto.CountrySummaryDto;
import com.anja.countries_backend.exception.CountryNotFoundException;
import com.anja.countries_backend.mapper.CountryMapper;

@Service
public class CountryService {

    private final RestCountriesClient client;
    private final CountryMapper mapper;

    public CountryService(RestCountriesClient client, CountryMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public List<CountrySummaryDto> getCountries(String name, String region, String sort) {
        List<RestCountryResponse> all = client.fetchAll();
        Stream<CountrySummaryDto> countries = (all == null ? List.<RestCountryResponse>of() : all)
                .stream()
                .map(mapper::toSummary);

        if (name != null && !name.isBlank()) {
            String query = name.trim().toLowerCase();
            countries = countries.filter(c -> c.name().toLowerCase().contains(query));
        }

        if (region != null && !region.isBlank()) {
            countries = countries.filter(c -> region.equalsIgnoreCase(c.region()));
        }

        return countries.sorted(buildComparator(sort)).toList();
    }

    public CountryDetailDto getCountry(String code) {
        RestCountryResponse response = client.fetchByCode(code);
        if (response == null) {
            throw new CountryNotFoundException(code);
        }
        return mapper.toDetail(response);
    }

    private Comparator<CountrySummaryDto> buildComparator(String sort) {
        Comparator<CountrySummaryDto> byPopulation =
                Comparator.comparingLong(CountrySummaryDto::population);

        if ("population,asc".equalsIgnoreCase(sort)) {
            return byPopulation;
        }
        if ("population,desc".equalsIgnoreCase(sort)) {
            return byPopulation.reversed();
        }
        return Comparator.comparing(CountrySummaryDto::name, String.CASE_INSENSITIVE_ORDER);
    }
}