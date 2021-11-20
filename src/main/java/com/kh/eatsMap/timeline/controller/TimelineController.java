package com.kh.eatsMap.timeline.controller;

import java.net.URI;
import java.net.URLEncoder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("timeline")
public class TimelineController {

	/* 지도 장소검색 테스트 */
	private static final String REST_API_KEY = "8f9fcce775dd72c4999049b1967438d8";
	private static final String API_SERVER_HOST  = "https://dapi.kakao.com";
    private static final String SEARCH_PLACE_KEYWORD_PATH = "/v2/local/search/keyword.json";
	
    public ResponseEntity<String> getSearchPlaceByKeyword(String keyword) throws Exception {
    	
        String queryString = "?sort=accuracy&query="+URLEncoder.encode(keyword, "UTF-8");
        //String queryString = "?query="+URLEncoder.encode(keyword.getKeywordNm(), "UTF-8")+"&page="+keyword.getCurrentPage()+"&size="+keyword.getPageSize();
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + REST_API_KEY);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        URI url = URI.create(API_SERVER_HOST + SEARCH_PLACE_KEYWORD_PATH + queryString);
        RequestEntity<String> rq = new RequestEntity<>(headers, HttpMethod.GET, url);
        ResponseEntity<String> re = restTemplate.exchange(rq, String.class);
        return re;
    }
    
	@GetMapping("/")
	public String timeline() throws Exception {
		ResponseEntity<String> test = getSearchPlaceByKeyword("신림 떡볶이");
		//System.out.println(test.);
		return "timeline/timeline";
	}
}
