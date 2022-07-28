package com.copark.elasticsearchexample.service.impl;

import com.copark.elasticsearchexample.adapter.AcademyAdapter;
import com.copark.elasticsearchexample.dto.SearchRequest;
import com.copark.elasticsearchexample.dto.StudentRequest;
import com.copark.elasticsearchexample.elasticrepository.ElasticStudentRepository;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import com.copark.elasticsearchexample.service.AcademyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO 6: ElasticRepository 과 요청할 RestTemplate 를 받는 Service 구현
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultAcademyService implements AcademyService {

    private final ElasticStudentRepository elasticRepository;
    private final AcademyAdapter academyAdapter;

    // TODO 8: 요청 객체를 받아 ElasticSearch Server 에 ElasticStudent 데이터 생성
    @Transactional
    @Override
    public void createStudent(StudentRequest studentRequest) {
        elasticRepository.save(new ElasticStudent(studentRequest));
    }

    // TODO 13: 직접 ElasticSearch Server 에 요청을 보낸 후 받은 데이터를 파싱하는 서비스 생성
    @Override
    public List<ElasticStudent> retrieveStudents(SearchRequest request)
            throws ParseException, JsonProcessingException {

        return academyAdapter.searchTest(request);
    }

    @Override
    public List<ElasticStudent> retrieveStudentsInfoContainKeyword(String keyword) {
        return elasticRepository.findAllByInfoContaining(keyword);
    }

    @Override
    public List<ElasticStudent> namingMethodTest(String keyword) {
        return elasticRepository.searchElasticStudentsByInfo(keyword, PageRequest.of(1, 2,
                                                                                     Sort.by(Sort.Direction.DESC,
                                                                                             "info.forSort")))
                                .getContent();
    }

    @Transactional
    @Override
    public void deleteStudent(String studentId) {
        ElasticStudent elasticStudent =
                elasticRepository.findById(studentId).orElseThrow(RuntimeException::new);
        elasticRepository.delete(elasticStudent);
    }

}

















