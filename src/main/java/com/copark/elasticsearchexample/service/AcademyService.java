package com.copark.elasticsearchexample.service;

import com.copark.elasticsearchexample.dto.StudentRequest;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;

import java.util.List;

public interface AcademyService {

    void createStudent(StudentRequest studentRequest);

    List<ElasticStudent> retrieveStudents();

    List<ElasticStudent> retrieveStudentsInfoContainKeyword(String keyword);

}
