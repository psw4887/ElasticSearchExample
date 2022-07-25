package com.copark.elasticsearchexample.dto;

import com.copark.elasticsearchexample.dto.bodydata.Query;
import com.copark.elasticsearchexample.dto.bodydata.Sort;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class RequestBody {

    private List<Sort> sort;

    private Integer from;

    private Integer size;

    private Query query;

}
