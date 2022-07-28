package com.copark.elasticsearchexample.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@AllArgsConstructor
@Getter
@Setter
public class SearchRequest {

    private String request;

    private PageRequest pageRequest;

}
