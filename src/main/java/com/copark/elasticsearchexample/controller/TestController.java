package com.copark.elasticsearchexample.controller;

import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import com.copark.elasticsearchexample.service.AcademyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/elastic")
@RequiredArgsConstructor
public class TestController {

    private final AcademyService academyService;

    // TODO 7: 응답 객체를 반환하는 Rest Controller 작성
    @GetMapping("/students")
    public ResponseEntity<List<ElasticStudent>> retrieveStudents() {

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/elastic/students"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(academyService.retrieveStudents());
    }

}
