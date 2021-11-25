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
		reviewRepository.findReviewByLocationNear(new Point(127.0956659043071, 37.546965436775125),
				new Distance(0.2, Metrics.KILOMETERS)).forEach(e -> log.info(e.toString()));
	}
	
	@Test
	public void searchKeywordTest() {
		List<Review> searchKeyword = reviewRepository.findReviewByResNameLike("마뇨");
		searchKeyword.forEach(e -> logger.debug(e.getResName()));
	}
	
	@Test
	public void findAllTest() {
		List<Review> findAll = reviewRepository.findAll();
		findAll.forEach(e -> logger.debug(e.getResName()));
	}
	
	@Test
	public void findCategoryTest() {
		List<Review> findCategory = reviewRepository.findReviewByCategoryLike("cg");
		findCategory.forEach(e -> logger.debug(e.getResName()));
	}
}
