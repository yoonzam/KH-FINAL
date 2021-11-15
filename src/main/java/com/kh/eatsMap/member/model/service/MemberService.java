package com.kh.eatsMap.member.model.service;

import java.util.List;

import com.kh.eatsMap.member.model.dto.Member;

public interface MemberService {

	List<Member> findMember();

	void joinMember();

}
