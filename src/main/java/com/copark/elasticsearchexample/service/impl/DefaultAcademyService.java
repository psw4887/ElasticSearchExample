package com.copark.elasticsearchexample.service.impl;

import com.copark.elasticsearchexample.adapter.AcademyAdapter;
import com.copark.elasticsearchexample.dto.StudentRequest;
import com.copark.elasticsearchexample.elasticrepository.ElasticStudentRepository;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import com.copark.elasticsearchexample.entity.jpa.Student;
import com.copark.elasticsearchexample.service.AcademyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO 9: ElasticRepository 과 요청할 RestTemplate 를 받는 Service 구현
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultAcademyService implements AcademyService {

    private final ElasticStudentRepository elasticRepository;

    private final ObjectMapper objectMapper;
    private final AcademyAdapter academyAdapter;

    @Transactional
    @Override
    public void createStudent(StudentRequest studentRequest) {
        elasticRepository.save(new ElasticStudent(studentRequest));
    }

    @Override
    public List<ElasticStudent> retrieveStudents() throws ParseException {
        List<ElasticStudent> list = new ArrayList<>();
        String response = academyAdapter.searchTest();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        JSONArray hitBody = (JSONArray) hits.get("hits");
        for (Object o : hitBody) {
            JSONObject source = (JSONObject) o;
            JSONObject body = (JSONObject) source.get("_source");
            list.add(new ElasticStudent(
                    new StudentRequest(body.get("id").toString(), body.get("name").toString(),
                                       body.get("info").toString())));
        }

        return list;
    }

    @Override
    public List<ElasticStudent> retrieveStudentsInfoContainKeyword(String keyword) {
        return elasticRepository.findAllByInfoContaining(keyword);
    }

    @Transactional
    @Override
    public void deleteStudent(String studentId) {
        ElasticStudent elasticStudent =
                elasticRepository.findById(studentId).orElseThrow(RuntimeException::new);
        elasticRepository.delete(elasticStudent);
    }
}

















