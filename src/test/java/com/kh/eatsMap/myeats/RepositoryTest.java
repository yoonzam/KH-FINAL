package com.kh.eatsMap.myeats;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.TypedSort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Streamable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;
import com.kh.eatsMap.timeline.model.dto.Review;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class RepositoryTest {
	
    @Autowired 
    private MemberRepository repository;
    
    @Autowired 
    private GroupRepository groupRepository;
    
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
	 public void getTotalCount() {
		 Integer a = (int)groupRepository.count();
		 System.out.println(a);
		 logger.debug("몇 명?"+a);
		}
	 
	@Test
	public void sortGroup() {
	 //List<Group> result = groupRepository.findAll(Sort.by(Sort.Direction.DESC, "groupIdx")); 
	 
	 List<Group> result = null;
	 result = mongoTemplate.findAll(com.kh.eatsMap.myeats.model.dto.Group.class,"group");
	 Query query = new Query();
	 query = query.with(Sort.by(Sort.Direction.DESC,"groupIdx"));	
	 query.skip(1 * 10);
	 
	 
	 result.forEach(e -> logger.debug(e.toString()));
	}
	
	@Test
	public void sortMember() {
	 
	 List<Member> result = null;
	 result = mongoTemplate.findAll(com.kh.eatsMap.member.model.dto.Member.class,"member");
	 Query query = new Query();
	 query = query.with(Sort.by(Sort.Direction.DESC,"id"));	
	 query.skip(1 * 10);
	 
	 
	 result.forEach(e -> logger.debug(e.toString()));
	}
	
	 @Test
	 public void getTotalCountMember() {
		 Integer a = (int)mongoTemplate.count(new Query(), "member"); 
		 System.out.println(a);
		 logger.debug("몇 명?"+a);
		}
	 
	 

		
		@Test
		public void findMember() {
		 
		 List<Member> result = null;
		 Query query = new Query();
		 query = query.with(Sort.by(Sort.Direction.DESC,"id"));	
		 query.skip(1 * 10);
		 query.addCriteria(Criteria.where("nickname").regex("잇츠잇츠"));
		 result = mongoTemplate.find(query,com.kh.eatsMap.member.model.dto.Member.class);
		 
		 
		 
		 result.forEach(e -> logger.debug(e.toString()));
		}
		
		@Test
		public void findMemberss() {
			List<Member> result = null;
			//잇츠를 포함하는
			String tagName = "잇츠";

			Query query = new Query();
			query = query.with(Sort.by(Sort.Direction.DESC,"id"));	
			query.addCriteria(Criteria.where("nickname").regex(tagName));

			result = mongoTemplate.find(query, com.kh.eatsMap.member.model.dto.Member.class);
		 
		 
		 result.forEach(e -> logger.debug(e.toString()));
		}
		//매개변수가 있는 메서드 테스트시 에러남 initialization

   
    
}