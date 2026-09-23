package com.anja.countries_backend.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RestCountriesPage(Data data) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(List<RestCountryResponse> objects, Meta meta) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Meta(int total, int count, int limit, int offset, boolean more) {}
}