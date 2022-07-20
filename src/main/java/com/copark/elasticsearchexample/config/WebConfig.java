package com.copark.elasticsearchexample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
// TODO 6: ElasticSearch Server 요청을 위한 RestTemplate 빈 등록 및 서버 IP 설정
@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofSeconds(10L))
                .setConnectTimeout(Duration.ofSeconds(5L))
                .build();
    }

    @Bean
    public String ElasticIp(@Value("${elasticsearch.url}") String ip) {
        return ip;
    }

}
