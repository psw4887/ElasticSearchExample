package com.copark.elasticsearchexample.dto.bodydata;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Sort {

    private Score _score;

    private Id id;

}
