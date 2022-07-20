package com.copark.elasticsearchexample.service;

import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;

import java.util.List;

public interface AcademyService {

    List<ElasticStudent> retrieveStudents();

}
