package com.kh.eatsMap.myeats;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;
import com.kh.eatsMap.timeline.model.dto.Review;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class GroupRepositoryTest {
	
    @Autowired 
    private GroupRepository groupRepository;
    
    @Autowired 
    private MemberRepository repository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    
    public static Group group;
    
    @Autowired 
	WebApplicationContext context;
    private MockMvc mockMvc;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(context).build();
	}
    
	 @Test
	    public void findByGroupIdx() {
		 groupRepository.findByGroupIdx("3").forEach(e -> logger.info(e.toString()));
	    }
	 
	
	 @Test
	    public void deleteById() {
		 groupRepository.deleteById("619779dce0c6e6637d4999ff");
	    }
	 
//	 @Test
//	    public void list() {
//		 groupRepository.findAll().forEach(e -> logger.info(e.toString()));
//	    }
//	 
	 @Test
	    public void list() {
		 
		 Query query = new Query();
		    query.addCriteria(Criteria.where("groupName").is(5));
		    mongoTemplate.find(query, Group.class).forEach(System.out::println);
		// mongoTemplate.findAll(com.kh.eatsMap.myeats.model.dto.Group.class).forEach(e -> logger.info(e.toString()));
	    }
	 
//	 @Test
//	 public void getTotalCount() {
//		 logger.debug("몇 명?"+ groupRepository.count());
//		}
	 
	 @Test
	 public void getTotalCount() {
		 Integer a = (int)groupRepository.count();
		 System.out.println(a);
		 logger.debug("몇 명?"+a);
		}
  
	 
	   @Test
	    public void findMemberById() {
	    	logger.debug(repository.findById("618df2c53519c259d27dc5ef").toString());
	    }
	 
	 @Test
	    public void sortMember() {
	    	Sort sort = Sort.by("nickname").descending();
	    	
	    	Query query = new Query().with(sort);
	    	List<Member> member = mongoTemplate.find(query, Member.class, "member");
	    	member.forEach(e -> logger.debug(e.toString()));
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
	    public void sortGroupa() {
		 
		 List<Group> result = null;
		 
		 
		 Query query = new Query();
		 //id로 잡아야 최신순으로 뜸
			query = query.with(Sort.by(Sort.Direction.DESC,"id"));
			query.skip(0 * 10);
			query.limit(10);
			//데이터를 가져와서 list에 채운다.
			result = mongoTemplate.find(query,com.kh.eatsMap.myeats.model.dto.Group.class,"group");
		 
		 
		 result.forEach(e -> logger.debug(e.toString()));
	    }
	 
	 
	 
	 

  
    

}
