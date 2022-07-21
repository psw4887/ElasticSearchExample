package com.copark.elasticsearchexample.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO 13: ElasticSearch Server 의 응답 값을 바인딩 해줄 Response 객체 생성
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudentRequest {

    private String id;

    private String name;

    private String info;

}
