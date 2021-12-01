package com.kh.eatsMap.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.code.Config;
import com.kh.eatsMap.common.mail.MailSender;
import com.kh.eatsMap.common.util.FileUtil;
import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Follower;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.member.model.repository.FollowerRepository;
import com.kh.eatsMap.member.model.repository.FollowingRepository;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.member.model.repository.MemberReviewRepository;
import com.kh.eatsMap.member.model.repository.NoticeRepository;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.JoinForm;
import com.kh.eatsMap.member.validator.ModifyForm;
import com.kh.eatsMap.myeats.model.dto.Like;
import com.kh.eatsMap.myeats.model.repository.LikeRepository;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final RestTemplate template;
	private final MailSender mailSender;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final NoticeRepository noticeRepository;
	private final FollowingRepository followingRepository;
	private final FileRepository fileRepository;
	private final MemberReviewRepository reviewRepository;
	private final FollowerRepository followerRepository;
	private final LikeRepository likeRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Member findMemberById(ObjectId id) {
		return memberRepository.findById(id);
	}
	
	@Override
	public void authenticateByEmail(JoinForm form, String token) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("mail-template", "join-auth-email");
		body.add("nickname", form.getNickname());
		body.add("persistToken", token);
		
		//RestTemplate의 기본 ContentType이 application/json이다.
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(Config.DOMAIN.DESC+"/mail")
													.accept(MediaType.APPLICATION_FORM_URLENCODED)
													.body(body);
		
		String htmlText = template.exchange(request, String.class).getBody();
		mailSender.sendEmail(form.getEmail(), "회원가입을 축하합니다.", htmlText);
	}

	@Override
	public void sendTmpPassword(EmailForm form, String tmpPassword) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("mail-template", "tmp-password-email");
		body.add("tmp", tmpPassword);
		
		//RestTemplate의 기본 ContentType이 application/json이다.
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(Config.DOMAIN.DESC+"/mail")
													.accept(MediaType.APPLICATION_FORM_URLENCODED)
													.body(body);
		
		String htmlText = template.exchange(request, String.class).getBody();
		mailSender.sendEmail(form.getEmail(), "[Eat's Map] 임시 비밀번호입니다.", htmlText);
	}

	@Override
	public Map<String,Object> authenticateUser(Member member) {
		Member storedMember = memberRepository.findByEmailAndIsLeave(member.getEmail(), 0);
	
		if(storedMember != null  && passwordEncoder.matches(member.getPassword(), storedMember.getPassword())) {
			Notice notice = noticeRepository.findByMemberId(storedMember.getId());
			int noticeCnt = notice.getCalendarNotice() + notice.getGroupNotice() + notice.getParticipantNotice() + notice.getFollowNotice();
			
			return Map.of("noticeCnt", noticeCnt, "notice", notice, "member",storedMember);
		}		
		return null;
	}

	@Override
	public void insertMember(JoinForm form) {
		Member member = new Member();	//mongoDB ValidatorForm <-> 객체매핑 X
		member.setEmail(form.getEmail());
		member.setPassword(passwordEncoder.encode(form.getPassword()));
		member.setRegDate();
		member.setNickname(form.getNickname());
		member.setIsLeave(0);
		memberRepository.save(member);

		Notice notice = new Notice();
		Member joinUser = memberRepository.findById(member.getId());
		notice.setMemberId(joinUser.getId());
		notice.setCalendarNotice(0);
		notice.setGroupNotice(0);
		notice.setParticipantNotice(0);
		notice.setFollowNotice(0);
		noticeRepository.save(notice);
	}


	@Override
	public void updatePassword(String email, String tmpPassword) {
		Member member = memberRepository.findByEmail(email);
		member.setPassword(tmpPassword);
		memberRepository.save(member);
	}

	@Override
	public Member findKakaoMember(String kakaoId) {
		return memberRepository.findByKakaoId(kakaoId);
	}

	@Override
	public void saveMember(Member member) {
		memberRepository.save(member);
	}

	@Override
	public Member findMemberByNickname(String nickname) {
		return memberRepository.findByNicknameIgnoreCase(nickname);
	}

	@Override
	public void updateMemberProfile(Member member, ModifyForm form, MultipartFile photo) {
		member.setNickname(form.getNickname());
		member.setPassword(passwordEncoder.encode(form.getPassword()));
		
		if(!photo.isEmpty()) {
			FileUtil fileUtil = new FileUtil();
			Fileinfo fileInfo = fileUtil.fileUpload(photo);
			fileInfo.setTypeId(memberRepository.findById(member.getId()).getId());
			fileRepository.save(fileInfo);
			
			logger.debug(fileInfo.toString());
			
			member.setProfile(fileInfo);
		}
		memberRepository.save(member);
	}

	@Override
	public void isLeaveMember(Member member) {
		member.setIsLeave(1);
		memberRepository.save(member);
	}

	@Override
	public void updateNotice(String noticeId, Notice notice) {
		switch (noticeId) {
		case "dday": notice.setCalendarNotice(1); break;
		case "calendar": notice.setParticipantNotice(1); break;
		case "follow": notice.setFollowNotice(1); break;
		case "group": notice.setGroupNotice(1); break;	}	
		
		noticeRepository.save(notice);
	}		
	
	@Override
	public void updateNoticeForDel(String id, Notice notice) {
		switch (id) {
		case "dday": notice.setCalendarNotice(0); break;
		case "calendar": notice.setParticipantNotice(0); break;
		case "follow": notice.setFollowNotice(0); break;
		case "group": notice.setGroupNotice(0); break;	}	
		
		noticeRepository.save(notice);
	}

	@Override
	public Map<String,Object> findMemberAndReviewByMemberId(ObjectId memberId) {
		
		Member member = memberRepository.findById(memberId);
		long followCnt = followingRepository.countByMemberId(memberId);
		long followerCnt = followerRepository.countByMemberId(memberId);
		
		List<Review> reviews = reviewRepository.findOptionalByMemberIdOrderByIdDesc(memberId).orElse(List.of());
		for (Review review : reviews) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
		}
		return Map.of("reviews", reviews, "member", member, "followCnt", followCnt, "followerCnt",followerCnt);
	}

	@Override
	public Follow findFollowByMemberId(ObjectId memberId, ObjectId id) {
		return followingRepository.findByMemberIdAndFollowingId(id,memberId).orElse(new Follow());
	}

	@Override
	public void followMember(ObjectId memberId, Follow followUser) {
		followUser.setMemberId(memberId);
		followingRepository.save(followUser);
		
		Follower follower = new Follower();
		follower.setMemberId(followUser.getFollowingId());	//팔로우 당할 유저 (팔로워 collection의 memberId)
		follower.setFollowerId(memberId);
		followerRepository.save(follower);
	}

	@Override
	public void followCancel(ObjectId id, Follow followUser) {
		followingRepository.findOptionalByMemberIdAndFollowingId(id, followUser.getFollowingId())
			.ifPresent(e -> followingRepository.delete(e));	//얘 검증필요
		
		followerRepository.findOptionalByMemberIdAndFollowerId(followUser.getFollowingId(), id)
			.ifPresent(e -> followerRepository.delete(e));
	}

	@Override
	public Notice findNotice(ObjectId followingId) {
		return noticeRepository.findByMemberId(followingId);
	}

	@Override
	public List<Review> findLikedByMemberId(Member member) {
		List<Like> likes = new ArrayList<Like>();
		List<Review> reviews = new ArrayList<Review>();
		likes = likeRepository.findByMemberId(member.getId());
		
		if(!likes.isEmpty()) {
			for (Like like : likes) {
				reviews.add(reviewRepository.findById(like.getRevId()).get());
			}
		}
		return reviews;
	}

	@Override
	public List<Map<String, Object>> findAllMemberToMap() {
		List<Map<String,Object>> memberList = new ArrayList<Map<String,Object>>();
		List<Member> members = memberRepository.findAll();
		
		for (Member member : members) {
			memberList.add(Map.of("memberId", member.getId().toString(), "member", member));
		}
		return memberList;
	}




}
