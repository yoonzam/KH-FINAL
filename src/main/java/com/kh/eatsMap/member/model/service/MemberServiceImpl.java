package com.kh.eatsMap.member.model.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private MemberRepository memberRepository;
	
	@Override
	public List<Member> findMember() {
		return memberRepository.findAll();
	}

	@Override
	public void joinMember() {
		
    	Member member = new Member();
    	member.setEmail("member@gmail.com");
    	member.setNickname("nick22");
    	member.setPassword("1234");
    	member.setIsLeave(0);
    	member.setRegDate(LocalDate.now());
    	
		memberRepository.save(member);
	}

}
