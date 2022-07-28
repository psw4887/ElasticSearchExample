package com.copark.elasticsearchexample.service;

import com.copark.elasticsearchexample.dto.SearchRequest;
import com.copark.elasticsearchexample.dto.StudentDto;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;

import java.util.List;

public interface AcademyService {

    void createStudent(StudentDto studentDto);

    List<ElasticStudent> retrieveStudents(SearchRequest request) throws ParseException, JsonProcessingException;

    List<ElasticStudent> retrieveStudentsInfoContainKeyword(String keyword);

    List<ElasticStudent> namingMethodTest(String keyword);

    void deleteStudent(String studentId);

}
