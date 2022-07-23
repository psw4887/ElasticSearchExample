package com.copark.elasticsearchexample.service;

import com.copark.elasticsearchexample.dto.StudentRequest;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AcademyService {

    void createStudent(StudentRequest studentRequest);

    List<ElasticStudent> retrieveStudents(String info, Pageable pageable);

    List<ElasticStudent> retrieveStudentsInfoContainKeyword(String keyword);

    void deleteStudent(String studentId);

}
