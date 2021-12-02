package com.kh.eatsMap.map.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.map.model.dto.Map;
import com.kh.eatsMap.map.model.repository.MapRepository;
import com.kh.eatsMap.map.model.repository.myMapRepository;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.repository.FollowingRepository;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.TimelineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

	@Autowired
	private final MapRepository mapRepository;

	@Autowired
	private final TimelineRepository timelineRepository;

	@Autowired
	private final FollowingRepository followingRepository;

	@Autowired
	private final myMapRepository myMapRepository;
	
	@Autowired
	private final GroupRepository groupRepository;

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

	@Override
	public List<Follow> findFollowList(ObjectId member_id) {
		// TODO Auto-generated method stub

		List<Follow> follows = followingRepository.findByMemberId(member_id);

		return follows;
	}

	@Override
	public List<HashMap<String, Object>> myEatsMap(ObjectId member_id, List<Follow> follows) {

		List<HashMap<String, Object>> mapList = new ArrayList<>();

		// 공개된 리뷰와 자신의 적은 리뷰를 가져온다.
		List<Review> reviews = mapRepository.findByMemberIdOrPrivacy(member_id, 0);
		// 팔로워로 표시된 리뷰만 가져오기
		List<Review> followReview = new ArrayList<Review>();

		for (Follow follow : follows) {
			followReview.addAll(mapRepository.findByMemberIdAndPrivacy(follow.getFollowingId(), 1));
		}
		System.out.println("내가 팔로워하여 볼수 있는 리뷰");
		System.out.println(followReview);
		/*
		 * reviews = reviews.stream().filter(e-> e.getPrivacy() == 0 || (e.getPrivacy()
		 * == 1 && e.getMemberId() == )
		 */
		System.out.println("공개된 리뷰");
		System.out.println(reviews);
		
		System.out.println("합쳐진 리뷰");
		reviews.addAll(followReview);
		System.out.println(reviews);

		/*
		 * reviews = reviews.stream() .filter(e -> e.getPrivacy() == 0 ||
		 * e.getMemberId().toString() == member_id.toString())
		 * .collect(Collectors.toList());
		 */

		// 본인이 쓴 리뷰 가져와야함
		// 팔로우한거 가져와야함
		// 프라이버시가 0인거 가져와야함
		

		for (Review review : reviews) {
			HashMap<String, Object> hashmap = new HashMap<>();
			hashmap.put("review", review);
			hashmap.put("reviewId", review.getId().toString());
			mapList.add(hashmap);
		}

		return mapList;
	}

	@Override
	public void insertMap(Map myMap) {
		// TODO Auto-generated method stub
		myMapRepository.save(myMap);

	}

	@Override
	public Map findByMemberId(ObjectId memberId) {

		return myMapRepository.findByMemberId(memberId);
	}

	@Override
	public List<Group> findGroupList(ObjectId id) {
		// TODO Auto-generated method stub
		return groupRepository.findByParticipants(id);
	}

}
