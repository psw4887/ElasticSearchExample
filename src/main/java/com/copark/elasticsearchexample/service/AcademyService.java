package com.copark.elasticsearchexample.service;

import com.copark.elasticsearchexample.dto.StudentRequest;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;
import org.json.simple.parser.ParseException;

public interface AcademyService {

    void createStudent(StudentRequest studentRequest);

    List<ElasticStudent> retrieveStudents() throws JsonProcessingException, ParseException;

    List<ElasticStudent> retrieveStudentsInfoContainKeyword(String keyword);

    void deleteStudent(String studentId);

}
