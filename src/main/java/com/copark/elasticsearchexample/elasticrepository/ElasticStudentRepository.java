package com.copark.elasticsearchexample.elasticrepository;

import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

// TODO 7: ElasticSearchRepository 를 상속받는 ElasticRepository 생성
public interface ElasticStudentRepository extends ElasticsearchRepository<ElasticStudent, String> {

    // TODO 11: 네이밍 메소드 사용
    List<ElasticStudent> findAllByInfoContaining(String keyword);

}
