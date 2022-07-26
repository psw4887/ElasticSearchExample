package com.copark.elasticsearchexample;

import com.copark.elasticsearchexample.elasticrepository.EsRepositoryMarker;
import com.copark.elasticsearchexample.repository.JpaRepositoryMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ElasticSearchExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchExampleApplication.class, args);
    }

}
