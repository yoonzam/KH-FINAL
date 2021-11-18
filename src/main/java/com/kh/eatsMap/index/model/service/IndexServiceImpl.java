package com.kh.eatsMap.index.model.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.index.model.repository.IndexRepository;
import com.kh.eatsMap.timeline.model.dto.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService{
	
//	private final IndexRepository indexRepository;
//
//	Logger logger = LoggerFactory.getLogger(this.getClass());
//	
//	@Override
//	public List<Review> findReview(String keyword, String[] category, String[] hashtag) {
//		// TODO Auto-generated method stub
//		return indexRepository.findReview(keyword, category, hashtag);
//	}

	
	
	
	//--hashtag픽(잇친아님) 
	//세션에서 현재 접속한 멤버의 id값 받아옴 
	//멤버의 like컬럼값이 1인 리뷰중(=내가 찜한 리뷰중) 높은 빈도의 hashtag 2개 선별
	//그 2개의 hashtag가 포함된 모든 리뷰 출력
	
	@Override
	public List<Review> findByHashtag(ObjectId id) {
		// TODO Auto-generated method stub
		return null;
	}


}
