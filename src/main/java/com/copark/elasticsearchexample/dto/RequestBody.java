package com.copark.elasticsearchexample.dto;

import com.copark.elasticsearchexample.dto.bodydata.MultiMatch;
import com.copark.elasticsearchexample.dto.bodydata.Query;
import java.util.Collections;
import java.util.List;
import lombok.Getter;


@Getter
public class RequestBody<T> {

    private static final List<String> DEFAULT_FIELD = List.of("info", "info.forEng");

    private final List<T> sort;

    private final Integer from;

    private final Integer size;

    private final Query query;

    public RequestBody(T sortMap, SearchRequest request) {
        this.sort = Collections.singletonList(sortMap);
        this. from = request.getPageRequest().getPageNumber();
        this.size = request.getPageRequest().getPageSize();
        this. query = new Query(new MultiMatch(request.getRequest(), DEFAULT_FIELD));
    }


}
