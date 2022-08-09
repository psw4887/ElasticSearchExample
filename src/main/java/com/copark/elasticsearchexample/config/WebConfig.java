package com.copark.elasticsearchexample.config;

import com.copark.elasticsearchexample.elasticrepository.EsRepositoryMarker;
import com.copark.elasticsearchexample.repository.JpaRepositoryMarker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
// TODO 2: ElasticSearch Repository 와 충돌 회피를 위한 Jpa Repository 패키지 위치 설정
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = { EsRepositoryMarker.class }),
                       basePackageClasses = { JpaRepositoryMarker.class })
@EnableElasticsearchRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = { JpaRepositoryMarker.class }),
                                 basePackageClasses = { EsRepositoryMarker.class })
@Configuration
public class WebConfig {

    // TODO 3: ElasticSearch Server 요청을 위한 RestTemplate 빈 등록 및 서버 IP 설정
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofSeconds(20L))
                .setConnectTimeout(Duration.ofSeconds(10L))
                .build();
    }

    @Bean
    public String elasticIp(@Value("${spring.elasticsearch.uris}") String ip) {
        return "http://elastic:123456@" + ip;
    }

}
