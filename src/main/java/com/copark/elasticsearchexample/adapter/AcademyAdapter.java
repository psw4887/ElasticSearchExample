package com.copark.elasticsearchexample.adapter;

import com.copark.elasticsearchexample.dto.RequestBody;
import com.copark.elasticsearchexample.dto.SearchRequest;
import com.copark.elasticsearchexample.dto.StudentRequest;
import com.copark.elasticsearchexample.dto.bodydata.Id;
import com.copark.elasticsearchexample.dto.bodydata.MultiMatch;
import com.copark.elasticsearchexample.dto.bodydata.Query;
import com.copark.elasticsearchexample.dto.bodydata.Score;
import com.copark.elasticsearchexample.dto.bodydata.Sort;
import com.copark.elasticsearchexample.entity.elastic.ElasticStudent;
import com.copark.elasticsearchexample.util.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AcademyAdapter {

    private final JSONParser jsonParser = new JSONParser();
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String elasticIp;
    private static final String DEFAULT_STUDENT = "/students/_search";

    // TODO 14: 직접 ElasticSearch Server 에 요청을 보내고 받은 Response 를 String 으로 반환
    public List<ElasticStudent> searchTest(SearchRequest request)
            throws ParseException, JsonProcessingException {

        Converter cv = new Converter();
        HttpEntity<String> requestEntity =
                new HttpEntity<>(
                        objectMapper.writeValueAsString(
                                this.buildKeywordRequestBody(request, cv.converter(request.getRequest()))),
                        this.buildHeaders());

        log.error(objectMapper.writeValueAsString(
                this.buildKeywordRequestBody(request, cv.converter(request.getRequest()))));

        return parsingResponseBody(doRequest(requestEntity).getBody());
    }

    // TODO 15: 요청 객체 생성
    private RequestBody buildKeywordRequestBody(final SearchRequest request, final String typo) {
        return RequestBody.builder()
                          .sort(List.of(new Sort(new Score("desc"), new Id("asc"))))
                          .from(request.getPageRequest().getPageNumber())
                          .size(request.getPageRequest().getPageSize())
                          .query(new Query(new MultiMatch(request.getRequest() + " " + typo,
                                                          List.of("info", "info.forEng"))))
                          .build();
    }

    // TODO 16: 요청
    private ResponseEntity<String> doRequest(final HttpEntity<String> request) {
        return restTemplate.exchange(elasticIp + DEFAULT_STUDENT,
                                     HttpMethod.POST,
                                     request,
                                     String.class);
    }

    // TODO 17: 응답 Body 분석 및 HitsBody 가져오기
    private List<ElasticStudent> parsingResponseBody(final String response)
            throws ParseException, JsonProcessingException {

        List<ElasticStudent> list = new ArrayList<>();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        JSONArray hitBody = (JSONArray) hits.get("hits");

        for (Object data : hitBody) {
            JSONObject source = (JSONObject) data;
            JSONObject body = (JSONObject) source.get("_source");
            list.add(new ElasticStudent(
                    objectMapper.readValue(body.toJSONString(), StudentRequest.class)));
        }

        return list;
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
