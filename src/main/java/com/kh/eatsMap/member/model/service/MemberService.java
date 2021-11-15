package com.kh.eatsMap.member.model.service;

import java.util.List;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.JoinForm;

public interface MemberService {

	List<Member> findMember();

	void joinMember();

	void authenticateByEmail(JoinForm form, String token);

	void sendTmpPassword(EmailForm form);

	Member authenticateUser(Member member);

}
