package com.kh.eatsMap.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
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
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Streamable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.eatsMap.calendar.model.dto.Calendar;
import com.kh.eatsMap.calendar.model.repository.CalendarRepository;
import com.kh.eatsMap.index.model.repository.ReviewRepository;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.member.model.repository.FollowingRepository;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.member.model.repository.NoticeRepository;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.timeline.model.dto.Review;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class MemberRepositoryTest {
	
    @Autowired 
    private MemberRepository repository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
	private CalendarRepository calendarRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private FollowingRepository followingRepository;
	@Autowired
	private MemberService memberService;
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
    	member.setEmail("ijij1135@gmail.com");
    	member.setNickname("jkl");
    	member.setPassword("1234");
    	member.setIsLeave(0);
    	member.setRegDate();
    	repository.save(member);
    	
    	//mongoTemplate.save(member);
    }
    
    @Test
    public void regDateTest() {
    	Member member = new Member();
    	member.setRegDate();
    	
    	logger.debug("regDate구하기 : " + member.getRegDate());
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
    	member.setRegDate();
    	
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

    @Test
    public void findByEmailAndIsLeave() {
    	logger.debug(repository.findByEmailAndIsLeave("qleen513@gmail.com", 1).toString());
    }
    
    @Test
    public void findReview() {
    	reviewRepository.findAll().forEach(e -> logger.debug(e.toString()));
    }

    @Test
    public void test() {
    	reviewRepository.findAll().forEach(e -> {
    		logger.debug(e.getId().toString());
    	});
    	
    }
    
    @Test
	public void pushAboutSchedule() {
    	
    	List<Date> dates = new ArrayList<Date>();
		calendarRepository.findAll().forEach(e -> {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(e.getDate().toString());
				//Date now = format.parse(new Date().toString());
				
				//현재시간보다 이후면서, 
				if(date.after(new Date()) && (date.getTime() - new Date().getTime()) / 3600000 < 24) {
					dates.add(date);
					logger.debug(e.toString());
				}
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});
		
		logger.debug(dates.toString());
	}
    
    @Test
    public void noticeTest() {
    	
    	Member member = repository.findByNickname("지원");
    	Notice notice = new Notice();
    	notice.setMemberId(member.getId());
    	notice.setCalendarNotice(0);
    	notice.setGroupNotice(0);
    	notice.setParticipantNotice(0);
    	notice.setFollowNotice(0);
    	noticeRepository.save(notice);
    
    }
    
    @Test
    public void findNotice() {
    	Member member = repository.findByNickname("잇츠잇츠");
    	logger.debug(noticeRepository.findByMemberId(member.getId()).toString());
    }
    
    @Test
    public void removeNotice() throws Exception {
    	Member member = repository.findByNickname("jkl");
    	Notice notice = new Notice();
    	notice.setMemberId(member.getId());
    	notice.setCalendarNotice(1);
    	notice.setGroupNotice(0);
    	notice.setParticipantNotice(0);
    	
    	mockMvc.perform(get("/member/removeNotice")
    			.param("id", "dday"))
    	.andExpect(status().isOk())
    	.andDo(print());
    }
    
    @Test
    public void followTest() throws Exception {
    	Member member = repository.findByNickname("잇츠잇츠");
    	mockMvc.perform(get("/member/follow/619e26bba6d62426e2e2aaf0"))
    	.andDo(print());
    			
    	
    }
    
    @Test
    public void deleteFollow() {
    	Member loginUser = repository.findByNickname("유진");
    	logger.debug(loginUser.toString());
    	Member member = repository.findByNickname("자몽");
    	logger.debug(member.toString());
    	Follow follow = followingRepository.findByMemberIdAndFollowingId(loginUser.getId(), member.getId()).get();
    	
    	logger.debug(follow.toString());
    	
    }
    
    @Test
    public void findFollowTest() {
    	Member loginUser = repository.findByNickname("유진");	//61a537408083d81456e2ff7b(일치)
    	logger.debug(loginUser.getId().toString());				//마루우 61a48ac95599286ec903cd01
    	Member member = repository.findByNickname("자몽");	//61a497d313f7451f784f869d(불일치)
    	logger.debug(member.getId().toString());
    	logger.debug("팔로우 찾아라 " + followingRepository.findOptionalByMemberIdAndFollowingId(loginUser.getId(),member.getId()).orElse(new Follow()));
    }
    @Test
    public void findAllFollowerToMapTest() {
    	Member member = repository.findByNickname("잇츠잇츠");
    	memberService.findAllFollowerToMap(member);

    }
    
}
