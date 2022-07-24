package com.copark.elasticsearchexample.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
@Getter
public class SearchRequest {

    private final String request;

    private final PageRequest pageRequest;

}
