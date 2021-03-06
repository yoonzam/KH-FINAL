//package com.kh.eatsMap.timeline;
//
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.List;
//
//import org.bson.types.ObjectId;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.kh.eatsMap.common.util.PageObject;
//import com.kh.eatsMap.member.model.dto.Member;
//import com.kh.eatsMap.myeats.model.dto.Group;
//import com.kh.eatsMap.myeats.model.dto.Like;
//import com.kh.eatsMap.myeats.model.repository.GroupRepository;
//import com.kh.eatsMap.myeats.model.repository.LikeRepository;
//import com.kh.eatsMap.timeline.model.dto.Review;
//import com.kh.eatsMap.timeline.model.repository.FileRepository;
//import com.kh.eatsMap.timeline.model.repository.TimelineRepository;
//import com.kh.eatsMap.timeline.model.service.TimelineService;
//
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
//public class TimelineMongoTest {
////	
////	@Autowired
////	TimelineRepository timelineRepository;
////	
////	@Autowired
////	FileRepository fileRepository;
////	
////	@Autowired
////	LikeRepository likeRepository;
////	
////	@Autowired
////	GroupRepository groupRepository;
////	
////	@Autowired
////	TimelineService timelineService;
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
////	
////	@Test //?????? ??????
////	public void saveRevies() {
////		Review review = new Review();
////		review.setMemberId(new ObjectId("6194d4e8b1271f6645746389"));
////		review.setResName("?????????");
////		review.setAddr("?????? ??????");
////		review.setReview("????????????");
////		review.setLocation(new GeoJsonPoint(127.0956659043071, 37.546965436775125));
////		review.setTaste(5);
////		review.setClean(5);
////		review.setService(3);
////		review.setCategory("cg01");
////		review.setHashtag(new String[]{ "md01", "pr01" });
////		review.setGroup("my");
////		review.setPrivacy(0);
////		timelineRepository.save(review);
////	}
////	
////	@Test //?????? ?????? ??????
////	public void findAllReviews() {
////		timelineRepository.findAll(Sort.by(Sort.Direction.DESC, "regDate")).forEach(e -> logger.info(e.toString()));
////	}
////	
////	@Test //?????? ?????? ?????? (???????????? ??????)
////	public void findAllSortId() {
////		logger.info(timelineRepository.findAll(Sort.by("id").descending()).toString());
////	}
////	
////	@Test //????????? ?????? ??????
////	public void findLastReview() {
////		Query query = new Query();
////		query = query.with(Sort.by(Sort.Direction.DESC, "id"));
////		Review review = mongoTemplate.findOne(query, com.kh.eatsMap.timeline.model.dto.Review.class, "review");
////		logger.info(review.toString());
////	}
////	
////	@Test //??????????????? ?????? ?????? (????????????)
////	public void findReviewByResName() {
////		Sort sort = Sort.by("resName").descending();
////		logger.info(timelineRepository.findByResNameOrderByIdAsc("?????????", sort).toString());
////	}
////	
////	@Test //ID??? ?????? ??????
////	public void findReviewById() {
////		logger.info(timelineRepository.findById("619f850835d7987fdb82f441").toString());
////	}
////	
////	@Test //ObjectId TimeStamp??? ?????? ?????????
////	public void getDateByTimeStamp() {	
////		ObjectId objectId = new ObjectId("61a0b2e2bb1acf3c5c30b70e");
////		long timeStamp = objectId.getTimestamp();
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E??????, a hh:mm:ss");
////		System.out.println(sdf.format(timeStamp*1000));
////	}
////	
////	@Test //????????????
////	public void saveLike() {
////		Like like = new Like();
////		like.setMemberId(new ObjectId("6194e8240b3d5d7684723834"));
////		like.setRevId(new ObjectId("61a4c61f728aaf3ab9374aa2"));
////		mongoTemplate.save(like);
////	}
////	
////	@Test //????????????
////	public void findLike() {
////		List<Like> like = likeRepository.findByMemberId(new ObjectId("6194e8240b3d5d7684723834"));
////		logger.info(like.toString());
////	}
////	
////	@Test //?????????????????? ?????????
////	public void categorySearch() {
////		Query query = new Query();
////	    Criteria criteria = new Criteria();
////	    
////	    String list[] = {"cg01","cg02"};
////	    Criteria criteriaArr[]  = new Criteria[list.length];
////	    
////	    for(int i = 0;i < list.length;i++){
////            String question = list[i];
////            criteriaArr[i] = Criteria.where("category").regex(question);
////        }	
////	    query.addCriteria( criteria.orOperator(criteriaArr) );
////	    
////	    List<Review> rsult = mongoTemplate.find(query, Review.class, "review");
////	    rsult.forEach(System.out::println);
////	}
////	
////	@Test //???????????? ?????? ?????????
////	public void areaSearch() {
////		Query query = new Query();
////		Criteria criteria = new Criteria();
////		
////		String list[] = {"??????","??????"};
////		Criteria criteriaArr[]  = new Criteria[list.length];
////		
////		for(int i = 0;i < list.length;i++){
////			String question = list[i];
////			criteriaArr[i] = Criteria.where("addr").regex(question);
////		}	
////		query.addCriteria( criteria.orOperator(criteriaArr) );
////		
////		List<Review> rsult = mongoTemplate.find(query, Review.class, "review");
////		rsult.forEach(System.out::println);
////	}
////	
////	@Test //???????????? ?????????
////	public void review() {
////		Member member = new Member();
////		member.setId(new ObjectId("619e26bba6d62426e2e2aaf0")); //?????????
////		PageObject pageObject = new PageObject(1, 8);
////		List<Review> reviews = timelineService.findAllReviews(pageObject, member);
////		
////		for (Review hashMap : reviews) {
////			System.out.println(hashMap.getMemberId()+","+hashMap.getPrivacy());
////		}
////	}
////	
////	@Test //???????????? ?????? ?????????
////	public void reviewSearch() {
////		Member member = new Member();
////		member.setId(new ObjectId("619e26bba6d62426e2e2aaf0")); //?????????
////		String[] area = {"02","032","031"};
////		String[] category = {"cg01","cg02","cg02"};
////		String[] hashtag = {"md01","md02","md03","md04"};
////		String keyword = "";
////
////		PageObject pageObject = new PageObject(1, 8);
////		//List<Review> reviews = timelineService.searchReview(keyword, area, category, hashtag, member, pageObject);
//////		System.out.println(reviews.isEmpty());
//////		for (Review hashMap : reviews) {
//////			System.out.println(hashMap.getResName() + ",	" + hashMap.getAddr() + ",	" + hashMap.getCategory().toString() + ",	" + hashMap.getHashtag().toString() + ",	" + hashMap.getPrivacy());
//////		}
////	}
////	
////	@Test
////	public void groupTest() {
////		List<Group> groups = groupRepository.findByMemberIdOrParticipants(new ObjectId("619e26bba6d62426e2e2aaf0"), new ObjectId("619e26bba6d62426e2e2aaf0"));
////	}
//
//}
