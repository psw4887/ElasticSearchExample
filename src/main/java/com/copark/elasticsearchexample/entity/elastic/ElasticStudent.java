package com.copark.elasticsearchexample.entity.elastic;

// TODO 7: ElasticSearch Server 에 담을 객체 생성

import com.copark.elasticsearchexample.dto.StudentRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "students")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ElasticStudent {

    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private String info;

    public ElasticStudent(StudentRequest studentRequest) {
        this.id = studentRequest.getId();
        this.name = studentRequest.getName();
        this.info = studentRequest.getInfo();
    }

}
