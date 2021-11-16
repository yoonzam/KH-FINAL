package com.kh.eatsMap.member;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.TypedSort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.timeline.model.dto.Timeline;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class MongoRepositoryTest {
	
    @Autowired 
    private MemberRepository repository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    
    
    @Test
    public void saveMember() {
    	Member member = new Member();
    	member.setEmail("kim@gmail.com");
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
    	
    	Sort sort = Sort.by("nickname").descending();
  			 

    	
    	Query query = new Query().with(sort);
    	List<Member> member = mongoTemplate.find(query, Member.class, "member");
    	logger.debug(member.toString());
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
    	List<Member> members = repository.findDistinctMemberByNicknameOrEmail("nick2","nick4@gmail.com");
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
		Page<Timeline> timeline = repository.findByNickname(nickname, page);
		
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
    	repository.findMemberByNickname("nick22").forEach(e -> logger.info(e.toString()));
    }
    
    @Test
    public void findMemberByEmail() {
    	logger.debug(repository.findByEmail("qwe@gmail.com").toString());
    }


}
