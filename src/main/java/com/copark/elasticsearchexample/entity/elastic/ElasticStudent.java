package com.copark.elasticsearchexample.entity.elastic;

import com.copark.elasticsearchexample.dto.StudentRequest;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

// TODO 4: ElasticSearch Server 에 담을 객체 생성
@Document(indexName = "students")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ElasticStudent {

    @Id
    @Field
    private Long id;

    @Field
    private String name;

    @Field
    private String info;

    public ElasticStudent(StudentRequest studentRequest) {
        this.id = studentRequest.getId();
        this.name = studentRequest.getName();
        this.info = studentRequest.getInfo();
    }

}
