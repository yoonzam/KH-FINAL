//package com.kh.eatsMap.map;
//
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//
//import java.time.LocalDate;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.geo.Distance;
//import org.springframework.data.geo.Metrics;
//import org.springframework.data.geo.Point;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.kh.eatsMap.map.model.dto.Map;
//import com.kh.eatsMap.map.model.repository.MapRepository;
//import com.kh.eatsMap.member.model.dto.Member;
//import com.kh.eatsMap.timeline.model.dto.Review;
//
//import lombok.extern.slf4j.Slf4j;
//
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*-context.xml" })
//@Slf4j
//public class MapMongoTest {
//
////	@Autowired
////	private MapRepository repository;
////
////	@Autowired
////	private MongoTemplate mongoTemplate;
////
////	@Autowired
////	WebApplicationContext context;
////	private MockMvc mockMvc;
////	Logger logger = LoggerFactory.getLogger(this.getClass());
////
////	@Before
////	public void setup() {
////		this.mockMvc = webAppContextSetup(context).build();
////	}
//
//	/*
//	 * @Test public void saveMember() { Map map = new Map();
//	 * 
//	 * repository.save(map);
//	 * 
//	 * // mongoTemplate.save(member); }
//	 */
//	
////	   @Test
////	    public void saveMember() {
////	    	Review re = new Review();
////	    	
////	    	
////	    	//mongoTemplate.save(member);
////	    }
////
////	@Test
////	public void geoTest() {
////		repository.findByLocationNear(new Point(127.026497, 37.507085),
////				new Distance(0.2, Metrics.KILOMETERS)).forEach(e -> log.info(e.toString()));
////	}
//
//}
