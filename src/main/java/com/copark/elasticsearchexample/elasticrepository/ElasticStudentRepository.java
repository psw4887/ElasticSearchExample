package com.copark.elasticsearchexample.elasticrepository;

import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

// TODO 5: ElasticSearchRepository 를 상속받는 ElasticRepository 생성
public interface ElasticStudentRepository extends ElasticsearchRepository<ElasticStudent, String> {

    // TODO 11: 네이밍 메소드 사용 (Pageable)
    Page<ElasticStudent> searchElasticStudentsByInfo(String keyword, Pageable pageable);

    // TODO 17: 쿼리 에노테이션 사용 가능
    @Query("{\"match\": {\"info\": {\"query\": \"?0\"}}}")
    List<ElasticStudent> findAllByInfoContaining(String keyword);

}
