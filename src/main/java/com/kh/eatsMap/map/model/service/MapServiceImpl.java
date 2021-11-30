package com.kh.eatsMap.map.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.map.model.repository.MapRepository;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.TimelineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService{

	
	@Autowired
	private final MapRepository mapRepository;
	
	@Autowired
	private final TimelineRepository timelineRepository;

	@Override
	public List<HashMap<String, Object>> reviewList() {
		List<HashMap<String, Object>> mapList = new ArrayList<>();
		
		List<Review> reviews = mapRepository.findAll();
		for (Review review : reviews) {
			HashMap<String, Object> hashmap = new HashMap<>();
			hashmap.put("review", review);
			hashmap.put("reviewId", review.getId().toString());
			mapList.add(hashmap);
		}
		
		return mapList;

	}

}
