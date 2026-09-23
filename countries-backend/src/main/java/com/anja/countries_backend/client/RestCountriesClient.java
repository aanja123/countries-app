package com.anja.countries_backend.client;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class RestCountriesClient {

    private static final int PAGE_SIZE = 100;
    private static final Duration CACHE_TTL = Duration.ofHours(1);

    private static final String SUMMARY_FIELDS =
        "names.common,codes.alpha_3,capitals,population,region,flag.url_png";
    private static final String DETAIL_FIELDS =
            "names.common,names.official,codes.alpha_3,capitals,population,region,subregion,"
                    + "area.kilometers,flag.url_png,flag.description,borders,timezones,languages,currencies";

    private final RestClient restClient;

    private List<RestCountryResponse> cachedCountries;
    private Instant cachedAt = Instant.EPOCH;

    public RestCountriesClient(RestClient restCountriesRestClient) {
        this.restClient = restCountriesRestClient;
    }

    public synchronized List<RestCountryResponse> fetchAll() {
        boolean expired = cachedAt.plus(CACHE_TTL).isBefore(Instant.now());
        if (cachedCountries == null || expired) {
            cachedCountries = loadAllPages();
            cachedAt = Instant.now();
        }
        return cachedCountries;
    }

    public RestCountryResponse fetchByCode(String code) {
        try {
            RestCountriesPage page = restClient.get()
                    .uri(b -> b.path("/codes.alpha_3/{code}")
                            .queryParam("response_fields", DETAIL_FIELDS)
                            .build(code))
                    .retrieve()
                    .body(RestCountriesPage.class);

            if (page == null || page.data() == null
                    || page.data().objects() == null || page.data().objects().isEmpty()) {
                return null;
            }
            return page.data().objects().get(0);
        } catch (HttpClientErrorException.NotFound | HttpClientErrorException.BadRequest ex) {
            return null;
        }
    }

    private List<RestCountryResponse> loadAllPages() {
        List<RestCountryResponse> all = new ArrayList<>();
        int offset = 0;
        boolean more = true;

        while (more) {
            RestCountriesPage.Data data = fetchPage(offset);
            all.addAll(data.objects());
            more = data.meta() != null && data.meta().more();
            offset += PAGE_SIZE;
        }
        return all;
    }

    private RestCountriesPage.Data fetchPage(int offset) {
        RestCountriesPage page = restClient.get()
                .uri(b -> b.queryParam("limit", PAGE_SIZE)
                        .queryParam("offset", offset)
                        .queryParam("response_fields", SUMMARY_FIELDS)
                        .build())
                .retrieve()
                .body(RestCountriesPage.class);

        if (page == null || page.data() == null || page.data().objects() == null) {
            throw new RestClientException("REST Countries returned an empty response.");
        }
        return page.data();
    }
}