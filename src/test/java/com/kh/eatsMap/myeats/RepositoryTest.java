package com.kh.eatsMap.myeats;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
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
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Streamable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.common.util.PageObject;
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

   
		@Test
		public void update(){
			Query query = new Query();
			Update update = new Update();
			query.addCriteria(Criteria.where("id").is("619cb6eb7e8be86c3929698e"));
			update.set("groupName", "ee");
			mongoTemplate.updateFirst(query, update, Group.class);
		}
		
		@Test
		public void list() {
			
			Member member = new Member();
			List<Group> list = null;
			Query query = new Query();
			query.addCriteria(Criteria.where("memberNickName").regex("yang"));
			query = query.with(Sort.by(Sort.Direction.DESC,"id"));
			list = mongoTemplate.find(query,com.kh.eatsMap.myeats.model.dto.Group.class,"group");
			
			list.forEach(e -> logger.debug(e.toString()));
		}
		
		//제거하고 추가하고 
				@Test
				public void updated(){
					Query query = new Query();
					query.addCriteria(Criteria.where("id").is("61a5cceab7ef8a4d0bf1413b"));
					Update update = new Update();
					update.set("groupName", "ee");
				    //데이터를 제거
				    update.pull("memberNickName", "댕댕이");  
				    mongoTemplate.updateFirst(query, update, Group.class);
				    
				    //데이터를 추가
				    String [] newItem = new String[]{"새로운애","새로운애"};
				    update = new Update();
				    update.push("memberNickName").each(newItem);
				    mongoTemplate.updateFirst(query, update, Group.class);    
				}
				
				//재테스트
				@Test
				public void listthree() {
					Member member = repository.findByNickname("yang");
					
					List<Group> list = new ArrayList<Group>();
					list = groupRepository.findByParticipants(member.getId());
					list.forEach(e -> logger.debug(e.toString()));
				}
				@Test
				public void listtwo() {
					
					Member member = repository.findByNickname("yang");
					List<Group> list = new ArrayList<Group>();
					Query query = new Query();
					query.addCriteria(Criteria.where("participants").in(member.getId()));
					query = query.with(Sort.by(Sort.Direction.DESC,"id"));
					list = mongoTemplate.find(query,com.kh.eatsMap.myeats.model.dto.Group.class,"group");
					
					list.forEach(e -> logger.debug(e.toString()));
				}
				
				 //objectId로 업뎃 성공
				@Test
				public void updatedd(){
					Query query = new Query();
					query.addCriteria(Criteria.where("id").is("61a7a2a6fa88ce490331292e"));
					Update update = new Update();
					update.set("groupName", "그룹1수정");
				    //데이터를 제거
					
				    update.pull("participants", new ObjectId("619f82ad35d7987fdb82f440"));  
				    mongoTemplate.updateFirst(query, update, Group.class);
				    
				    //데이터를 추가
				    ObjectId[] newItem = new ObjectId[]{new ObjectId("619f82ad35d7987fdb82f440"),new ObjectId("619f82ad35d7987fdb82f440")};
				    update = new Update();
				    update.push("participants").each(newItem);
				    mongoTemplate.updateFirst(query, update, Group.class);    
				}
				
				@Test
				public void del(){
				
				Query fileDelQuery = new Query();
				fileDelQuery.addCriteria(Criteria.where("typeId").is("61a9d5ae1ba5a378dc0a7d7f"));
				mongoTemplate.remove(fileDelQuery, com.kh.eatsMap.common.util.Fileinfo.class);
 
				}
				
				//회원아이디로 그룹나가기
				@Test
				 public void groupLeaveById() throws Exception{
						
						
						Query query = new Query();
						query.addCriteria(Criteria.where("id").is("61a7a2e3fa88ce4903312932"));
						Update update = new Update();
						
						update.pull("participants", new ObjectId("61a4a78a421834204011fc49")); 
						mongoTemplate.updateFirst(query, update, Group.class);
						
						
						}
}
