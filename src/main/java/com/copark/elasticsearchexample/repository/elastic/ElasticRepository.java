package com.copark.elasticsearchexample.repository.elastic;

import com.copark.elasticsearchexample.entity.elastic.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

// TODO 8: ElasticSearchRepository 를 상속받는 ElasticRepository 생성
public interface ElasticRepository extends ElasticsearchRepository<Student, Long> {

}
