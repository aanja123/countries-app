package com.anja.countries_backend.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RestCountryResponse(
        Names names,
        Codes codes,
        List<Capital> capitals,
        Long population,
        String region,
        String subregion,
        Area area,
        Flag flag,
        List<String> borders,
        List<String> timezones,
        List<Language> languages,
        List<Currency> currencies) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Names(String common, String official) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Codes(@JsonProperty("alpha_3") String alpha3) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Capital(String name) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Area(Double kilometers) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Flag(
            @JsonProperty("url_png") String urlPng,
            @JsonProperty("url_svg") String urlSvg,
            String description) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Language(String name, @JsonProperty("native_name") String nativeName) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Currency(String code, String name, String symbol) {}
}