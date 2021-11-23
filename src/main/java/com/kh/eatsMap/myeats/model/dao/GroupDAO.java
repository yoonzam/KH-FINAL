package com.kh.eatsMap.myeats.model.dao;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.dto.PageObject;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;

@Repository
public class GroupDAO {
	
	private static GroupRepository groupRepository;
	
	 @Autowired
	 private MongoTemplate mongoTemplate;
	 
	 @Autowired
	private static Group group;
	
	public List<Group> list(PageObject pageObject) {
		System.out.println("GroupDAO.list()");
		
		//db.group.find().sort({_id:-1})
		//.skip((page-1) * perPageNum).limit(perPageNum)
		
		List<Group> list = null;
		//findAll된 자료에서 원하는 데이터만 가져오기 위한 Query객체 생성 - 조건이 없는 쿼리 객체 생성
		Query query = new Query();
		//groupIdx로 최근순 정렬
		//id로 잡으면 알아서 최신순 정렬 됨
		query = query.with(Sort.by(Sort.Direction.DESC,"id"));
		//이전 페이지의 데이터는 skip 시킨다.
		query.skip((pageObject.getPage()-1) * pageObject.getPerPageNum());
		//한 페이지에 표시할 데이터만큼만 가져오기
		//limit이 int만 받음
		query.limit((int)pageObject.getPerPageNum());
		//데이터를 가져와서 list에 채운다.
		list = mongoTemplate.find(query,com.kh.eatsMap.myeats.model.dto.Group.class,"group");
		
		System.out.println("GroupDAO.list().list : " + list);
		return list;
		
	}
	
	//전체 게시글의 개수
	public Integer getTotalCount() {
		System.out.println("GroupDAO.getTotalCount()");
		//new Query()(조건없는 쿼리)
		//group테이블에서 조건 없는 쿼리를 통해 모든 데이터를 꺼내와 개수를 헤아림
		return (int)mongoTemplate.count(new Query(), "group"); 
	}
	
	
}
