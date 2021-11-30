package com.kh.eatsMap.map.model.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.map.model.dto.Map;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface MapRepository extends MongoRepository<Review, String>{

	//List<Review> findByLocationWithin(Circle circle);
	//List<Review> findByLocationWithin(Box box);
	List<Review> findByLocationNear(Point location, Distance distance);
	
	//List<Review> findByResNameOrderByIdAsc(String resName, Sort sort);

}
