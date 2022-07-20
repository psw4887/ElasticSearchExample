package com.copark.elasticsearchexample.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

// TODO 3: ElasticSearch Configuration 설정 및 Repository 위치 설정
@EnableElasticsearchRepositories(basePackages = { "com.copark.elasticsearchexample.repository.elastic" })
@Configuration
@Slf4j
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    private String hostname;

    @Value("${elasticsearch.port}")
    private Integer port;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                                                                     .connectedTo(hostname + ":" + port)
                                                                     .build();

        return RestClients.create(clientConfiguration).rest();
    }

}
