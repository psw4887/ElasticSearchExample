package com.copark.elasticsearchexample.controller;

import com.copark.elasticsearchexample.dto.StudentRequest;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import com.copark.elasticsearchexample.service.AcademyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elastic/students")
@RequiredArgsConstructor
public class TestController {

    private final AcademyService academyService;

    private static final String DEFAULT_ELASTIC = "/elastic/students";

    // TODO 10: 요청 객체를 받아 ElasticSearch Server 에 ElasticStudent 데이터 생성
    @PutMapping("/create")
    public ResponseEntity<StudentRequest> createStudent(@RequestBody StudentRequest studentRequest) {
        academyService.createStudent(studentRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("PUT" + DEFAULT_ELASTIC + "/create"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(studentRequest);
    }

    // TODO 16: 직접 ElasticSearch Server 에 요청 보내기위한 메소드를 실행 할 Rest Controller 생성
    @GetMapping
    public ResponseEntity<List<ElasticStudent>> retrieveStudents() throws JsonProcessingException,
            ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("GET" + DEFAULT_ELASTIC))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(academyService.retrieveStudents());
    }

    // TODO 12: Info 에 Keyword 를 포함하는 학생 목록을 응답 객체를 반환하는 Rest Controller 작성
    @GetMapping("/info/search")
    public ResponseEntity<List<ElasticStudent>> retrieveStudentsInfoContainKeyword(@RequestParam String keyword) {
        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("GET" + DEFAULT_ELASTIC + "/info/search"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(academyService.retrieveStudentsInfoContainKeyword(keyword));
    }

    @DeleteMapping("/{studentId}/delete")
    public ResponseEntity<String> deleteStudent(@PathVariable String studentId) {
        academyService.deleteStudent(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("DELETE" + DEFAULT_ELASTIC + "/" + studentId + "/delete"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentId);
    }

}
