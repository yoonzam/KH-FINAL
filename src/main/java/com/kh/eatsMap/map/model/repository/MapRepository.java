package com.kh.eatsMap.map.model.repository;

import java.util.List;

import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.map.model.dto.Map;
import com.kh.eatsMap.member.model.dto.Member;

public interface MapRepository extends MongoRepository<Map, String>{

	List<Map> findByLocationWithin(Circle circle);
	List<Map> findByLocationWithin(Box box);
	List<Map> findByLocationNear(Point location, Distance distance);

}
