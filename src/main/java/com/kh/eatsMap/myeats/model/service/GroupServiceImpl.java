package com.kh.eatsMap.myeats.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.code.Config;
import com.kh.eatsMap.common.mail.MailSender;
import com.kh.eatsMap.common.util.FileUtil;
import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.common.util.FindCriteria;
import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.repository.FollowerRepository;
import com.kh.eatsMap.member.model.repository.FollowingRepository;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.member.model.repository.MemberReviewRepository;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.JoinForm;
import com.kh.eatsMap.myeats.model.dao.GroupDAO;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.dto.Like;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;
import com.kh.eatsMap.myeats.model.repository.LikeRepository;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.FileRepository;
import com.kh.eatsMap.timeline.model.repository.TimelineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
	
	private final RestTemplate template;
	private final MailSender mailSender;
	private final GroupRepository groupRepository;
	
	private final TimelineRepository timelineRepository;
	private final FileRepository fileRepository;
	
	//post페이징
	private final MemberRepository memberRepository;
	private final FollowingRepository followingRepository;
	private final FollowerRepository followerRepository;
	//detail페이징
	private final LikeRepository likeRepository;
	private final MemberReviewRepository reviewRepository;
	
	 @Autowired
	 private MongoTemplate mongoTemplate;
	
	 @Autowired
	 private final GroupDAO dao;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	//검색기능
		@Override
		public List<Member> listMemberFind(FindCriteria findCri) throws Exception{
			return dao.listFind(findCri);
		}
		
		@Override
		public int findMemberCountData(FindCriteria findCri) throws Exception{
			return dao.findCountData(findCri);
		}
		
		
	//닉네임으로 멤버 리스트 부르기
		@Override
		public List<Member> listMemberFindByNickName(String nickname) throws Exception{
			return dao.listMemberFindByNickName(nickname);
		}
		
		
	
	//페이징 및 조회/group.jsp
	@Override
	public List<Group> list(PageObject pageObject, Member member){
		//GroupDAO의 전체 행의 개수를 받아서 pageObject객체의 TotalRow변수 초기화
		pageObject.setTotalRow(dao.getTotalCount());
		return dao.list(pageObject, member);
	}
	
	//groupIdx로 Group 읽어드리기
	@Override
	public List<Group> read(ObjectId id){
		return groupRepository.findById(id);
	}
	
	
	
	//파일 업로드 및 write
	@Override
	public void write(Group group, List<MultipartFile> photos, Member member) {
		
		group.setMemberId(member.getId());
		groupRepository.save(group);
		
		FileUtil fileUtil = new FileUtil();
		for (MultipartFile photo : photos) {
			if(!photo.isEmpty()) {
				Fileinfo fileInfo = fileUtil.fileUpload(photo);
				fileInfo.setTypeId(group.getId());
				fileRepository.save(fileInfo);
			}
		}
	}
	//파일 업로드
	@Override
	public List<Fileinfo> findFiles(ObjectId id) {
		return fileRepository.findByTypeId(id);
	}

	
	//id로 Group 삭제, groupRepository사용해서 id로 받음
	@Override
	public void remove(String id){
		//String StringgroupIdx = Integer.toString(id);
		groupRepository.deleteById(id);
	}
	
	//수정하기
	@Override
	public void modify(Group group,MultipartFile photo, Member member,
			ObjectId delNickNameOne,ObjectId delNickNameTwo,ObjectId delNickNameThree,
			ObjectId delNickNameFour,ObjectId delNickNameFive,ObjectId delNickNameSix,
			ObjectId newNickNameOne,ObjectId newNickNameTwo,ObjectId newNickNameThree,
			ObjectId newNickNameFour,ObjectId newNickNameFive,ObjectId newNickNameSix) throws Exception{
		dao.update(group,photo,member,
				delNickNameOne,delNickNameTwo,delNickNameThree,
				delNickNameFour,delNickNameFive,delNickNameSix,
				newNickNameOne,newNickNameTwo,newNickNameThree,
				newNickNameFour,newNickNameFive,newNickNameSix);
	}
	
	@Override
	 public Member findMemberById(ObjectId id) {
		Member member = new Member();
		member = dao.findMemberById(id);
		return member;
	}
	@Override
	 public  List<Member> findMember() throws Exception{
		List<Member> members = dao.listMember();
		return members;
	}
	
	@Override
	public  List<Member> findMemberlistById(ObjectId id) throws Exception{
		List<Member> members = dao.listMemberbyId(id);
		return members;
	}
	@Override
	public Group findGroupById(ObjectId id) throws Exception{
		Group group = new Group();
		group = dao.findGroupById(id);
		return group;
	}
	
	//회원의 그룹나가기
	@Override
	public void groupLeave(Group group,ObjectId id) throws Exception{
		dao.groupLeaveById(group,id);
	}
	//Post Paging 처리
	@Override
	public Map<String,Object> findMemberAndReviewByMemberIdPage(PageObject pageObject,ObjectId memberId) {
		
		Member member = memberRepository.findById(memberId);
		long followCnt = followingRepository.countByMemberId(memberId);
		long followerCnt = followerRepository.countByMemberId(memberId);
		List<Review> reviews = null;
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC,"id"));
		query.addCriteria(Criteria.where("memberId").is(memberId));
		
		query.skip((pageObject.getPage()-1) * pageObject.getPerPageNum());
		query.limit((int)pageObject.getPerPageNum());
		
		reviews = mongoTemplate.find(query,com.kh.eatsMap.timeline.model.dto.Review.class);
		
		
//		List<Review> reviews = reviewRepository.findOptionalByMemberIdOrderByIdDesc(memberId).orElse(List.of());
		for (Review review : reviews) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
		}
		return Map.of("reviews", reviews, "member", member,"memberId", member.getId().toString(), "followCnt", followCnt, "followerCnt",followerCnt);
	}
	@Override
	public Integer getTotalCount() {
		return (int)mongoTemplate.count(new Query(), "review"); 
	}
	
	@Override
	public Integer getTotalCountBymemberId(ObjectId memberId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("memberId").is(memberId));
		return (int)mongoTemplate.count(query, "review"); 
		
		
	}
	//group Count
	@Override
	public Integer getTotalCountGroupBymemberId(ObjectId memberId) {
		Query query = new Query();
		query.addCriteria(
			    Criteria.where("").orOperator(
			            Criteria.where("memberId").is(memberId),
			            Criteria.where("participants").in(memberId)
			        )
			    );
		return (int)mongoTemplate.count(query, "group"); 
		
		
	}
	
	//Detail Paging 처리
	@Override
	public List<Review> findLikedByMemberId(PageObject pageObject,Member member) {
		List<Like> likes = new ArrayList<Like>();
		List<Review> reviews = new ArrayList<Review>();
		likes = likeRepository.findByMemberId(member.getId());
		
		if(!likes.isEmpty()) {
			for (Like like : likes) {
				reviews.add(reviewRepository.findById(like.getRevId()).orElse(new Review()));
			}
		}
		
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC,"id"));
		query.addCriteria(Criteria.where("memberId").is(member.getId()));
		
		query.skip((pageObject.getPage()-1) * pageObject.getPerPageNum());
		query.limit((int)pageObject.getPerPageNum());
		
		reviews = mongoTemplate.find(query,com.kh.eatsMap.timeline.model.dto.Review.class);
		return reviews;
	}





}
