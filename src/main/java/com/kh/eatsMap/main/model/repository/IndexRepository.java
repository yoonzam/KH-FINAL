package com.kh.eatsMap.main.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Member;

public interface IndexRepository extends MongoRepository<Member, String>{
	
	List<Member> findMemberById(String memberId);

	
	}
