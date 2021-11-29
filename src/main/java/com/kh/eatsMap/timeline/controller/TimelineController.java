package com.kh.eatsMap.timeline.controller;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.myeats.model.dto.PageObject;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.service.TimelineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("timeline")
public class TimelineController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final TimelineService timelineService;
	
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
	public String timeline(Model model) {
		PageObject pageObject = new PageObject(1, 4);
		List<Review> reviews = timelineService.findAllReviews(pageObject);
		model.addAttribute("reviews", reviews);
		return "timeline/timeline";
	}
	
	@PostMapping("/")
	@ResponseBody
	public List<HashMap<String, Object>> timelinePaging(Model model, int page) {
		PageObject pageObject = new PageObject(page, 4);
		return timelineService.findReviewsForPaging(pageObject);
	}
	
	@PostMapping("upload")
	@ResponseBody
	public void upload(Review review, double latitude, double longitude, List<MultipartFile> photos, @SessionAttribute("authentication") Member member) {
		timelineService.insertReview(review, latitude, longitude, photos, member);
	}
	
	@GetMapping("detail")
	@ResponseBody
	public Map<String, Object> detail(String id) {
		return timelineService.findReviewById(id);
	}
	
	@GetMapping("edit")
	@ResponseBody
	public Map<String, Object> edit(String id) {
		return timelineService.findReviewById(id);
	}
	
	@PostMapping("edit")
	@ResponseBody
	public void editRevice(
			Review review, String reviewId, List<MultipartFile> photos, String latitude, String longitude, @RequestParam(value="hdPhotos") List<String> hdPhotos,
			@SessionAttribute("authentication") Member member) {
		timelineService.editReview(review, reviewId, member, latitude, longitude, photos, hdPhotos);
	}
	
	@PostMapping("delete")
	@ResponseBody
	public void deleteRevice(@RequestParam(value = "id") String id, @SessionAttribute("authentication") Member member) {
		timelineService.deleteReview(id, member);
	}
}
