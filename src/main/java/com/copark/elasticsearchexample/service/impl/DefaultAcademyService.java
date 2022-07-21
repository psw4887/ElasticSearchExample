package com.copark.elasticsearchexample.service.impl;

import com.copark.elasticsearchexample.elasticrepository.ElasticStudentRepository;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import com.copark.elasticsearchexample.repository.StudentRepository;
import com.copark.elasticsearchexample.service.AcademyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// TODO 11: ElasticRepository 과 요청할 RestTemplate 를 받는 Service 구현
@Service
@RequiredArgsConstructor
public class DefaultAcademyService implements AcademyService {

    private final StudentRepository studentRepository;
    private final ElasticStudentRepository elasticRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<ElasticStudent> retrieveStudents() {
        return elasticRepository.findAll(PageRequest.of(0, 10)).getContent();
    }

}
