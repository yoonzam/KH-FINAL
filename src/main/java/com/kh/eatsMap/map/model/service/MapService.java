package com.kh.eatsMap.map.model.service;

import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;

import com.kh.eatsMap.map.model.dto.Map;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface MapService {


	List<HashMap<String, Object>> reviewList();

	
	List<Follow> findFollowList(ObjectId member_id);


	List<HashMap<String, Object>> myEatsMap(ObjectId member_id, List<Follow> follows);


	void insertMap(Map myMap);


	Map findByMemberId(ObjectId id);


	List<Group> findGroupList(ObjectId id);


	List<HashMap<String, Object>> findMemberList(String groupId);




}
