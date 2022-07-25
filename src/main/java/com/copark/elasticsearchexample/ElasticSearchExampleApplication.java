package com.copark.elasticsearchexample;

import com.copark.elasticsearchexample.repository.JpaRepositoryMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// TODO 4: ElasticSearch Repository 와 충돌 회피를 위한 Jpa Repository 패키지 위치 설정
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = { JpaRepositoryMarker.class })
public class ElasticSearchExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchExampleApplication.class, args);
    }

}
