package com.copark.elasticsearchexample.controller;

import com.copark.elasticsearchexample.entity.jpa.Student;
import com.copark.elasticsearchexample.service.AcademyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/elastic")
@RequiredArgsConstructor
public class TestController {

    private final AcademyService academyService;

    // TODO 7: 응답 객체를 반환하는 Rest Controller 작성
    public ResponseEntity<List<Student>> retrieveStudents() {

    }

}
