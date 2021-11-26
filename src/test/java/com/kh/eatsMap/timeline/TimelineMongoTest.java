package com.kh.eatsMap.timeline;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.TimelineRepository;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class TimelineMongoTest {
	
	@Autowired
	TimelineRepository timelineRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	WebApplicationContext context;
	private MockMvc mockMvc;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(context).build();
	}
	
	@Test //리뷰 저장
	public void saveRevies() {
		Review review = new Review();
		review.setMemberId(new ObjectId("6194d4e8b1271f6645746389"));
		review.setResName("식당명");
		review.setAddr("식당 주소");
		review.setReview("리뷰내용");
		review.setLocation(new GeoJsonPoint(127.0956659043071, 37.546965436775125));
		review.setTaste(5);
		review.setClean(5);
		review.setService(3);
		review.setCategory("cg01");
		review.setHashtag(new String[]{ "md01", "pr01" });
		review.setGroup("my");
		review.setPrivacy(0);
		timelineRepository.save(review);
	}
	
	@Test //모든 리뷰 찾기
	public void findAllReviews() {
		timelineRepository.findAll(Sort.by(Sort.Direction.DESC, "regDate")).forEach(e -> logger.info(e.toString()));
	}
	
	@Test //마지막 리뷰 찾기
	public void findLastReview() {
		Review review = null;
		Query query = new Query();
		query = query.with(Sort.by(Sort.Direction.DESC, "id"));
		review = mongoTemplate.findOne(query, com.kh.eatsMap.timeline.model.dto.Review.class, "review");
		logger.debug(review.toString());
	}
	
	@Test //식당명으로 리뷰 찾기 (내림차순)
	public void findReviewByResName() {
		Sort sort = Sort.by("resName").descending();
		logger.info(timelineRepository.findByResNameOrderByIdAsc("라쿠치나", sort).toString());
	}
	
	@Test //ID로 리뷰 찾기
	public void findReviewById() {
		logger.info(timelineRepository.findById("619f850835d7987fdb82f441").toString());
	}
}
