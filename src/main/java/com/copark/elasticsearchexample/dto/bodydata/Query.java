package com.copark.elasticsearchexample.dto.bodydata;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Query {

    private MultiMatch multi_match;

}
