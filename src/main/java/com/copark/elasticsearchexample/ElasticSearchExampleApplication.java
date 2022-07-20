package com.copark.elasticsearchexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// TODO 5: ElasticSearch Repository 와 충돌 회피를 위한 Jpa Repository 패키지 위치 설정
@SpringBootApplication
@EnableJpaRepositories("com.copark.elasticsearchexample.repository")
public class ElasticSearchExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchExampleApplication.class, args);
    }

}
