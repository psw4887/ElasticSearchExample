package com.copark.elasticsearchexample.service.impl;

import com.copark.elasticsearchexample.repository.elastic.ElasticRepository;
import com.copark.elasticsearchexample.repository.jpa.StudentRepository;
import com.copark.elasticsearchexample.service.AcademyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcademyServiceImpl implements AcademyService {

    private final StudentRepository studentRepository;
    private final ElasticRepository elasticRepository;

}
