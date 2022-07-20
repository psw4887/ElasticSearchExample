package com.copark.elasticsearchexample.entity.elastic;

// TODO 9: ElasticSearch Server 에 담을 객체 생성

import com.copark.elasticsearchexample.dto.StudentCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document(indexName = "students")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ElasticStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String info;

    // TODO 10: ElasticSearch Server 에 저장된 Document 를 Aggregate 로 재구성하기
    @PersistenceConstructor
    public ElasticStudent(StudentCreateRequest studentRequest) {
        this.name = studentRequest.getName();
        this.info = studentRequest.getInfo();
    }

}
