package com.kh.eatsMap.index.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Member;

public interface IndexRepository extends MongoRepository<Member, String>{
	
	Member findMemberById(String memberId);

	
}
