package com.copark.elasticsearchexample.service.impl;

import com.copark.elasticsearchexample.dto.StudentRequest;
import com.copark.elasticsearchexample.elasticrepository.ElasticStudentRepository;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import com.copark.elasticsearchexample.service.AcademyService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO 8: ElasticRepository 과 요청할 RestTemplate 를 받는 Service 구현
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultAcademyService implements AcademyService {

    private final ElasticStudentRepository elasticRepository;

    @Transactional
    @Override
    public void createStudent(StudentRequest studentRequest) {
        elasticRepository.save(new ElasticStudent(studentRequest));
    }

    // TODO 13: 직접 ElasticSearch Server 에 요청을 보낸 후 받은 데이터를 파싱하는 서비스 생성
    @Override
    public List<ElasticStudent> retrieveStudents(String info, Pageable pageable) {
        return elasticRepository.findAllStudents(info).getContent();
    }

    @Override
    public List<ElasticStudent> retrieveStudentsInfoContainKeyword(String keyword) {

        return elasticRepository.findAllByInfoContaining(keyword);
    }

    @Transactional
    @Override
    public void deleteStudent(String studentId) {
        ElasticStudent elasticStudent = elasticRepository.findById(studentId).orElseThrow(RuntimeException::new);
        elasticRepository.delete(elasticStudent);
    }

}

















