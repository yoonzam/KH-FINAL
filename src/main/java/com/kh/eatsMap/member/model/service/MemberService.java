package com.kh.eatsMap.member.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.JoinForm;

public interface MemberService {

	void authenticateByEmail(JoinForm form, String token);

	void sendTmpPassword(EmailForm form, String tmpPassword);

	Member authenticateUser(Member member);

	void insertMember(JoinForm persistUser);

	void insertProfileImg(Member member, MultipartFile file);

	void updatePassword(String email, String tmpPassword);

}
