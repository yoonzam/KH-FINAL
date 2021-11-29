package com.kh.eatsMap.map.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.map.model.dto.Map;
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
	public List<Review> reviewList() {
		System.out.println("리뷰 서치 동작중?");
		Sort sort = Sort.by("resName").descending();
		List<Review> reviews = mapRepository.findAll();
		System.out.println(reviews.toString());
		return reviews;
	}

}
