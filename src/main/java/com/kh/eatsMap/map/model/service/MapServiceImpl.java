package com.kh.eatsMap.map.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	private MapRepository mapRepository;
	
	@Autowired
	private TimelineRepository timelineRepository;

	@Override
	public List<Review> reviewList(String keyword) {
		
		
		
		return null;
	}

}
