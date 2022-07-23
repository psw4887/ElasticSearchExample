package com.copark.elasticsearchexample.service.impl;

import com.copark.elasticsearchexample.adapter.AcademyAdapter;
import com.copark.elasticsearchexample.dto.StudentRequest;
import com.copark.elasticsearchexample.elasticrepository.ElasticStudentRepository;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import com.copark.elasticsearchexample.service.AcademyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO 8: ElasticRepository 과 요청할 RestTemplate 를 받는 Service 구현
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

    // TODO 13: 직접 ElasticSearch Server 에 요청을 보낸 후 받은 데이터를 파싱하는 서비스 생성
    @Override
    public List<ElasticStudent> retrieveStudents(String info) throws ParseException, JsonProcessingException {
        List<ElasticStudent> list = new ArrayList<>();
        JSONArray hitBody = getHitBody(academyAdapter.searchTest());

        for (Object data : hitBody) {
            JSONObject source = (JSONObject) data;
            JSONObject body = (JSONObject) source.get("_source");
            list.add(new ElasticStudent(objectMapper.readValue(body.toJSONString(), StudentRequest.class)));
        }

        return list;
    }

    @Override
    public List<ElasticStudent> retrieveStudentsInfoContainKeyword(String keyword) {

        return elasticRepository.findAllByInfoContaining(keyword);
    }

    @Override
    public List<ElasticStudent> namingMethodTest(String keyword) {
        return elasticRepository.searchElasticStudentByInfo(keyword, PageRequest.of(1, 2)).getContent();
    }

    @Transactional
    @Override
    public void deleteStudent(String studentId) {
        ElasticStudent elasticStudent =
                elasticRepository.findById(studentId).orElseThrow(RuntimeException::new);
        elasticRepository.delete(elasticStudent);
    }

    private JSONArray getHitBody(String response) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        return (JSONArray) hits.get("hits");
    }

}

















