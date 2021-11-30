package com.kh.eatsMap.timeline;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.text.SimpleDateFormat;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.myeats.model.dto.Like;
import com.kh.eatsMap.myeats.model.repository.LikeRepository;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.FileRepository;
import com.kh.eatsMap.timeline.model.repository.TimelineRepository;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class TimelineMongoTest {
	
	@Autowired
	TimelineRepository timelineRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	LikeRepository likeRepository;
	
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
	
	@Test //모든 리뷰 찾기 (아이디로 정렬)
	public void findAllSortId() {
		logger.info(timelineRepository.findAll(Sort.by("id").descending()).toString());
	}
	
	@Test //마지막 리뷰 찾기
	public void findLastReview() {
		Query query = new Query();
		query = query.with(Sort.by(Sort.Direction.DESC, "id"));
		Review review = mongoTemplate.findOne(query, com.kh.eatsMap.timeline.model.dto.Review.class, "review");
		logger.info(review.toString());
	}
	
	@Test //식당명으로 리뷰 찾기 (내림차순)
	public void findReviewByResName() {
		Sort sort = Sort.by("resName").descending();
		logger.info(timelineRepository.findByResNameOrderByIdAsc("알마또", sort).toString());
	}
	
	@Test //ID로 리뷰 찾기
	public void findReviewById() {
		logger.info(timelineRepository.findById("619f850835d7987fdb82f441").toString());
	}
	
	@Test //ObjectId TimeStamp로 시간 구하기
	public void getDateByTimeStamp() {	
		ObjectId objectId = new ObjectId("61a0b2e2bb1acf3c5c30b70e");
		long timeStamp = objectId.getTimestamp();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E요일, a hh:mm:ss");
		System.out.println(sdf.format(timeStamp*1000));
	}
	
	@Test //맛찜하기
	public void saveLike() {
		Like like = new Like();
		like.setMemberId(new ObjectId("6194e8240b3d5d7684723834"));
		like.setRevId(new ObjectId("61a4c61f728aaf3ab9374aa2"));
		mongoTemplate.save(like);
	}
	
	@Test //맛찜찾기
	public void findLike() {
		List<Like> like = likeRepository.findByMemberId(new ObjectId("6194e8240b3d5d7684723834"));
		logger.info(like.toString());
	}

}
