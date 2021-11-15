package com.kh.eatsMap.timeline.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kh.eatsMap.timeline.model.dto.Timeline;

public interface TimelineRepository extends MongoRepository<Timeline, String>{

}
