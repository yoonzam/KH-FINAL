package com.kh.eatsMap.member;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bson.Document;
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
import com.kh.eatsMap.timeline.model.dto.Review;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class MemberRepositoryTest {
	
    @Autowired 
    private MemberRepository repository;
    
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
    public void saveMember() {
    	Member member = new Member();
    	member.setEmail("qleen513@gmail.com");
    	member.setNickname("kim");
    	member.setPassword("1234");
    	member.setIsLeave(0);
    	member.setRegDate(LocalDate.now());
    	repository.save(member);
    	
    	//mongoTemplate.save(member);
    }
    
    
    @Test
    public void findAllMembers() {
    	repository.findAll().forEach(e -> logger.info(e.toString()));
    }
    
    @Test
    public void memberCount() {
    	logger.debug("몇 명?"+ repository.count());
//    	repository.count(null);
    }
    
    @Test
    public void sortMember() {
    	Sort sort = Sort.by("nickname").descending();
    	
    	Query query = new Query().with(sort);
    	List<Member> member = mongoTemplate.find(query, Member.class, "member");
    	member.forEach(e -> logger.debug(e.toString()));
    }

    
    @Test
    public void existMemberById() {
    	logger.debug("아이디가 ~인 멤버 존재? " + repository.existsById("618df2c53519c259d27dc5ef"));
    }
    
    @Test
    public void findMemberById() {
    	logger.debug(repository.findById("618df2c53519c259d27dc5ef").toString());
    }
    
    
    
    
    @Test
    public void findMember() {
    	//distinct
    	List<Member> members = repository.findDistinctMemberByNicknameOrEmail("nick22","nick4@gmail.com");
    	logger.debug(members.toString());
    }
	
    @Test
    public void findMemberIgnoreCase() {
    	//ignoreCase
    	logger.debug(repository.findByNicknameIgnoreCase("NICK2").toString());
    }
	
	@Test
	public void findAllMemberIgnoreCase() {
		logger.info(repository.findByNicknameOrEmailAllIgnoreCase("NICK2","NIck4@Gmail.com").toString());
	}
	
	@Test
	public void findMemberWithOrderBy() {
		//order by		
		logger.info(repository.findByPasswordOrderByNicknameAsc("1234").toString());
		
	}
	
	
	//*  [spring-data-core api] org.springframeword.data.domain
	//1. pageable : element 및 page의 수를 알려줌
	//2. slice : 다음 Slice가 사용가능한지를 반환
	//3. sort : 단순히 정렬할 거면 매개변수로 Sort만 전달
	
	@Test
	public void findTimelineWithPage() {
		String nickname = "nick2";
		Pageable page = (Pageable) Page.empty();
		Page<Review> timeline = repository.findByNickname(nickname, page);
		
		//Slice<Member> findById(String id, Pageable page);
	}
	
    @Test
    public void findMemberWithSort() {
    	Sort sort = Sort.by("nickname").descending()
    			  .and(Sort.by("email").descending());
    	
    	//Type_safe
    	TypedSort<Member> person = Sort.sort(Member.class);

    	Sort sortByType = person.by(Member::getNickname).ascending()
    	  .and(person.by(Member::getEmail).descending());
    	
    	
    	List<Member> members = repository.findByPassword("1234", sort);
    	logger.info(members.toString());
    }
    
    @Test
    public void findMemberOne() {
    	logger.info(repository.findFirstByOrderByNicknameAsc().toString());
    	//findTopByOrderByNicknameAsc() 과 동일
    }
    
    @Test
    public void findMemberWithTopBySort() {
    	Sort sort = Sort.by("nickname").descending();
    	
    	//sort 조건에 의해 정렬 후, nickname이 nick5인 회원 10명 조회
    	logger.info(repository.findTop10ByNickname("nick5",sort).toString());
    }
    
    @Test
    public void findMemberByNickname() {
    	//repository.findMemberByNickname("지우지마수").forEach(e -> logger.info(e.toString()));
    	logger.debug(repository.findByNickname("지우지마수").toString());
    }
    
    @Test
    public void findMemberByEmail() {
    	Optional<Member> member = Optional.ofNullable(repository.findByEmail("3@gmail.com"));
    	member.ifPresent(e -> logger.debug(e.toString()));
    	
    }

    @Test
    public void deleteMember() {
    	Member member = new Member();
    	member.setEmail("qleen513@gmail.com");
    	member.setNickname("kim");
    	member.setPassword("1234");
    	member.setIsLeave(0);
    	member.setRegDate(LocalDate.now());
    	
    	repository.delete(member);
    }

    @Test
    public void findMemberByContaining() {
    	Streamable<Member> result = repository.findByNicknameContaining("oo")
    			  .and(repository.findByEmailContaining("qw"));
    	
    	result.forEach(e -> logger.debug(e.toString()));
    }
    
    @Test
    public void findMemberByQuery() {
    	try (Stream<Member> result = repository.findMemberByQuery()) {
			result.forEach(e -> logger.debug(e.toString()));
		}
    }
    
    @Test
    public void kakaoJoinTest() throws Exception {
    	Member member = new Member();
    	member.setKakaoId("12354dfd");
    	member.setPassword("1234");
    	
    	ObjectMapper mapper = new ObjectMapper();
    	String memberJson = mapper.writeValueAsString(member);
    	
    	mockMvc.perform(post("/member/kakao-login")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(memberJson))
    	.andExpect(status().isOk())
    	.andDo(print());
    }

    

    
}
