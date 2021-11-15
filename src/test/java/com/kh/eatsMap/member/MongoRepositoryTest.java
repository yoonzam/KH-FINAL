package com.kh.eatsMap.member;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.TypedSort;
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
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void findAllMembers() {
      List<Member> members = repository.findAll();
      repository.findAll();
      repository.findById("");
//      repository.save(member);
      
      
    }
    
    @Test
    public void memberCount() {
    	logger.info("몇 명?"+ repository.count());
    }

    
    @Test
    public void existMemberById() {
    	logger.info("아이디가 ~인 멤버 존재? " + repository.existsById("618df2c53519c259d27dc5ef"));
    }
    
    @Test
    public void findMemberById() {
    	logger.info(repository.findById("618df2c53519c259d27dc5ef").toString());
    }
    
    
    
    
    @Test
    public void findMember() {
    	//distinct
    	List<Member> members = repository.findDistinctMemberByNicknameOrEmail("nick2","nick2@gmail.com");
    	logger.info(members.toString());
    }
	
    @Test
    public void findMemberIgnoreCase() {
    	//ignoreCase
    	logger.info(repository.findByNicknameIgnoreCase("NICK2").toString());
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
	


}
