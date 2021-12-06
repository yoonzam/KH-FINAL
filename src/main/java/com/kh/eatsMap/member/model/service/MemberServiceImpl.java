package com.kh.eatsMap.member.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.SessionAttribute;
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
	public void updatePassword(String email, String tmpPassword) {
		Member member = memberRepository.findByEmail(email);
		member.setPassword(passwordEncoder.encode(tmpPassword));
		memberRepository.save(member);
	}

	@Override
	public Map<String,Object> authenticateUser(Member member) {
		Member storedMember = memberRepository.findByEmailAndIsLeave(member.getEmail(), 0);
	
		if(storedMember != null  && passwordEncoder.matches(member.getPassword(), storedMember.getPassword())) {
			Notice notice = findNoticeByMemberId(storedMember.getId());
			int noticeCnt = notice.getCalendarNotice() + notice.getGroupNotice() + notice.getParticipantNotice() + notice.getFollowNotice();
			
			return Map.of("noticeCnt", noticeCnt, "notice", notice, "member",storedMember);
		}		
		return null;
	}

	@Override	
	public Notice findNoticeByMemberId(ObjectId memberId) {
		return noticeRepository.findByMemberId(memberId).orElse(new Notice());
	}

	@Override
	public void insertMember(JoinForm form) {
		Member member = new Member();	//mongoDB ValidatorForm <-> 객체매핑 X
		member.setEmail(form.getEmail());
		member.setPassword(passwordEncoder.encode(form.getPassword()));
		member.setRegDate();
		member.setNickname(form.getNickname());
		member.setIsLeave(0);
		Member joinUser = memberRepository.save(member);

		saveNoticeWhenJoin(joinUser);
	}


	@Override
	public Member findKakaoMember(String kakaoId) {
		return memberRepository.findByKakaoIdAndIsLeave(kakaoId, 0).orElse(new Member());
	}

	@Override
	public void saveMember(Member member) {
		memberRepository.save(member);
	}
	
	@Override
	public void saveMemberBySocial(Member member) {
		member.setRegDate();
		member.setIsLeave(0);
		Member joinUser = memberRepository.save(member);
		
		saveNoticeWhenJoin(joinUser);
	}
	
	public void saveNoticeWhenJoin(Member member) {
		Notice notice = findNoticeByMemberId(member.getId());
		
		if(notice.getId() == null) {
			notice.setMemberId(member.getId());
			notice.setCalendarNotice(0);
			notice.setGroupNotice(0);
			notice.setParticipantNotice(0);
			notice.setFollowNotice(0);			
		}
		noticeRepository.save(notice);
	}

	@Override
	public Member findMemberByNickname(String nickname) {
		return memberRepository.findByNicknameIgnoreCase(nickname);
	}

	@Override
	public void updateMemberProfile(Member member, ModifyForm form, MultipartFile photo) {
		if(form != null) {
			member.setNickname(form.getNickname());
			member.setPassword(passwordEncoder.encode(form.getPassword()));
		}
		
		if(!photo.isEmpty()) {
			FileUtil fileUtil = new FileUtil();
			Fileinfo fileInfo = fileUtil.fileUpload(photo);
			fileInfo.setTypeId(memberRepository.findById(member.getId()).getId());
			fileRepository.save(fileInfo);
			
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
	public Map<String,Object> findMemberAndReviewByMemberId(ObjectId memberId, Member loginUser) {
		List<Review> reviews = new ArrayList<Review>();
		Member member = memberRepository.findById(memberId);
		long followCnt = followingRepository.countByMemberId(memberId);
		long followerCnt = followerRepository.countByMemberId(memberId);
		Follow follow = findFollowByMemberId(memberId, loginUser.getId());
		
		if (memberId.equals(loginUser.getId())) {
			reviews = reviewRepository.findOptionalByMemberIdOrderByIdDesc(memberId).orElse(List.of());
		}else {
			if (follow != null) {
				reviews = reviewRepository.findOptionalByMemberIdAndPrivacyNotOrderByIdDesc(memberId , -1).orElse(List.of());
			}else {
				reviews = reviewRepository.findOptionalByMemberIdAndPrivacyOrderByIdDesc(memberId , 0).orElse(List.of());
			}			
		}
		
		for (Review review : reviews) {
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
			if(files.size() > 0) review.setThumUrl(files.get(0).getDownloadURL());
		}
		return Map.of("reviews", reviews, "member", member,"memberId", member.getId().toString(),"follow", follow, "followCnt", followCnt, "followerCnt",followerCnt);
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
	public List<Review> findLikedByMemberId(Member member) {
		List<Like> likes = new ArrayList<Like>();
		List<Review> reviews = new ArrayList<Review>();
		likes = likeRepository.findByMemberId(member.getId());
		
		if(!likes.isEmpty()) {
			for (Like like : likes) {
				reviews.add(reviewRepository.findById(like.getRevId()).orElse(new Review()));
			}
		}
		return reviews;
	}

	@Override
	public List<Map<String, Object>> findAllFollowingToMap(Member member) {
		List<Map<String,Object>> memberList = new ArrayList<Map<String,Object>>();
		List<Member> friendsInfo = new ArrayList<Member>();
		List<Follow> friends = followingRepository.findByMemberId(member.getId()).orElse(List.of());
		
		if(!friends.isEmpty()) {
			for (Follow friend : friends) {
				friendsInfo.add(memberRepository.findById(friend.getFollowingId()));
			}	
			for (Member info : friendsInfo) {
				memberList.add(Map.of("memberId", info.getId().toString(), "member", info));
			}
		}
		return memberList;
	}

	@Override
	public Member findByStringId(String memberId) {
		return memberRepository.findById(memberId).orElse(new Member());
	}

	@Override
	public Map<String, Object> findAllFollowerToMap(Member member) {
		
		List<Map<String,Object>> memberList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> followEachOtherId = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> followDiffId = new ArrayList<Map<String, Object>>();
		List<Member> followerInfo = new ArrayList<Member>();
		List<Follower> followEachOther = new ArrayList<Follower>();
		
		List<Follower> followers = followerRepository.findByMemberId(member.getId()).orElse(List.of());
		List<Follow> followings = followingRepository.findByMemberId(member.getId()).orElse(List.of());	
		
		if(!followers.isEmpty()) {
			//내 팔로워 중 내가 팔로우하지 않은 유저 조회
			if(!followings.isEmpty()) {
				for (Follower follower : followers) {
					for (Follow following : followings) {
						if(following.getFollowingId().equals(follower.getFollowerId())) {
							followEachOther.add(follower);
						}
					}
				}
				
				for (Follower follow : followEachOther) {
					followEachOtherId.add(Map.of("memberId", follow.getFollowerId().toString()));
				}
			}
			
			for (Follower follower : followers) {
				followerInfo.add(memberRepository.findById(follower.getFollowerId()));
			}	
			for (Member info : followerInfo) {
				memberList.add(Map.of("memberId", info.getId().toString(), "member", info));
			}
			//차집합
			followers.removeAll(followEachOther);
			
			for (Follower follow : followers) {
				followDiffId.add(Map.of("memberId", follow.getFollowerId().toString()));
			}
		}		

		return Map.of("memberInfo", memberList, "followEachOther", followEachOtherId, "followDiffId", followDiffId);
	}

	@Override
	public boolean quitImpl(Member member) {
		Member authUser = findMemberById(member.getId());
		if (!authUser.getNickname().equals(member.getNickname())) return false;
		if (!member.getPassword().equals("") && member.getPassword() != null) return passwordEncoder.matches(member.getPassword(), authUser.getPassword());
		
		return true;
	}




}
