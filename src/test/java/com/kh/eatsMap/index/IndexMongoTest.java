package com.kh.eatsMap.index;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.kh.eatsMap.index.model.repository.IndexRepository;
import com.kh.eatsMap.index.model.repository.ReviewRepository;
import com.kh.eatsMap.timeline.model.dto.Review;

import lombok.extern.slf4j.Slf4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
@Slf4j
public class IndexMongoTest {

    @Autowired 
    private ReviewRepository reviewRepository;

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

    
	@Test
	public void geoTest() {
		List<Review> findReviewByLocationNear = reviewRepository.findByPrivacyAndLocationNear(0, new Point(126.8989355, 37.4064155), new Distance(50, Metrics.KILOMETERS));
		findReviewByLocationNear.forEach(e -> logger.debug(e.getResName()));
	}
	
//	@Test
//	public void searchKeywordTest() {
//		List<Review> searchKeyword = reviewRepository.findReviewByResNameContaining("그슬");
//		searchKeyword.forEach(e -> logger.debug("여기를보세요!!!!!!!!!!!!!!"+e.getResName()));
//	}
	
	@Test
	public void findAllTest() {
		List<Review> findAll = reviewRepository.findAll();
		findAll.forEach(e -> logger.debug(e.getResName()));
	}
	
//	@Test
//	public void findCategoryTest() {
//		List<Review> findCategory = reviewRepository.findReviewByCategoryLike(new String[] {"cg04"});
//		findCategory.forEach(e -> logger.debug("여기를보세요!!!!!!!!!!!!!!"+e.getResName()));
//	}
	@Test
	public void findHashtagTest() {
		List<Review> findHash = reviewRepository.findReviewByPrivacyAndHashtagLike(0, new String[] {"md03", "md04"});
		findHash.forEach(e -> logger.debug("여기를보세요!!!!!!!!!!!!!!"+e.getResName()));
	}

	//좋아요 한 리뷰찾기 
	@Test
	public void findReviewByLike() {
		List<Review> findLike = reviewRepository.findReviewByLike(1);
		findLike.forEach(e -> logger.debug(e.getResName()));
	}
	
//	@Test
//	public void findFirstByIdOrderByCategoryDesc() {
//		Review findcate = reviewRepository.findFirstByIdOrderByCategoryDesc(new ObjectId("619f7d9494b88f684d44195d"));
//		logger.debug(findcate.getResName());
//	}
	
	
	
	
	
	
}
