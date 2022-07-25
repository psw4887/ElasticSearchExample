package com.copark.elasticsearchexample.dto.bodydata;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MultiMatch {

    private String query;

    private List<String> fields;

}
