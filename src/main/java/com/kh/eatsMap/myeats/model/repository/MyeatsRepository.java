package com.kh.eatsMap.myeats.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.member.model.dto.Member;

public interface MyeatsRepository extends MongoRepository<Member, String>{

}
