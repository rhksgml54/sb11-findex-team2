package com.sprint.mission.findex.domain.syncclient.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.findex.domain.syncclient.dto.IndexDataApiResponse;
import com.sprint.mission.findex.domain.syncclient.dto.KrxApiResponseWrapper;
import com.sprint.mission.findex.global.exception.ApiException;
import com.sprint.mission.findex.global.exception.ApiException.ERROR;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

@Component
public class KrxOpenApiClientImpl implements KrxOpenApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final String apiKey;

    public KrxOpenApiClientImpl(
            @Value("${public-data.base-url}") String baseUrl,
            @Value("${public-data.api-key}") String apiKey,
            ObjectMapper objectMapper
    ) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<IndexDataApiResponse> fetchByDateRange(String indexName, LocalDate from, LocalDate to) {
        try {
            String encodedIndexName = UriUtils.encode(indexName, StandardCharsets.UTF_8);

            String url = baseUrl + "/getStockMarketIndex"
                    + "?serviceKey=" + apiKey
                    + "&resultType=json"
                    + "&pageNo=1"
                    + "&numOfRows=1000"
                    + "&beginBasDt=" + from.format(DateTimeFormatter.BASIC_ISO_DATE)
                    + "&endBasDt=" + to.format(DateTimeFormatter.BASIC_ISO_DATE)
                    + "&idxNm=" + encodedIndexName;


            URI uri = URI.create(url);
            String responseBody = restTemplate.getForObject(uri, String.class);


            if (responseBody == null || responseBody.isBlank()) {
                return Collections.emptyList();
            }

            KrxApiResponseWrapper wrapper =
                    objectMapper.readValue(responseBody, KrxApiResponseWrapper.class);

            if (wrapper.getResponse() == null
                    || wrapper.getResponse().getBody() == null
                    || wrapper.getResponse().getBody().getItems() == null
                    || wrapper.getResponse().getBody().getItems().getItem() == null) {
                return Collections.emptyList();
            }

            return wrapper.getResponse().getBody().getItems().getItem();

        } catch (RestClientException e) {
            throw new ApiException(ERROR.SYNC_JOB_OPEN_API_ERROR);
        } catch (Exception e) {
            throw new ApiException(ERROR.SYNC_JOB_OPEN_API_ERROR);
        }
    }
}