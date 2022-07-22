package com.copark.elasticsearchexample.adapter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AcademyAdapter {

    private final RestTemplate restTemplate;
    private final String elasticIp;
    private static final String DEFAULT_STUDENT = "/students/_doc";

    // TODO 14: 직접 ElasticSearch Server 에 요청을 보내고 받은 Response 를 String 으로 반환
    public String searchTest() {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<String> response =
                restTemplate.exchange(elasticIp + DEFAULT_STUDENT + "/_search",
                                      HttpMethod.POST,
                                      requestEntity,
                                      String.class);
        return response.getBody();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
