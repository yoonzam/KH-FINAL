package com.kh.eatsMap.member.model.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.JoinForm;
import com.kh.eatsMap.member.validator.ModifyForm;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface MemberService {

	void authenticateByEmail(JoinForm form, String token);

	void sendTmpPassword(EmailForm form, String tmpPassword);

	Map<String, Object> authenticateUser(Member member);

	void insertMember(JoinForm persistUser);

	void insertProfileImg(Member member, MultipartFile file);

	void updatePassword(String email, String tmpPassword);

	Member findKakaoMember(String kakaoId);

	//카카오용 임시메서드
	void saveMember(Member member);

	Member findMemberByNickname(String nickname);

	void updateMemberProfile(Member member, ModifyForm modifyForm);

	void isLeaveMember(Member member);

	void updateNotice(String id, Notice notice);

	Map<String,Object> findMemberAndReviewByMemberId(ObjectId memberId);

	Follow findFollowByMemberId(ObjectId memberId, ObjectId id);

	void followMember(ObjectId memberId, Follow followUser);

	void followCancel(ObjectId id, Follow followUser);

}
