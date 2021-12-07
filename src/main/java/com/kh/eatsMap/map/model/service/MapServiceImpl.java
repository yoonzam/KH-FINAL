package com.kh.eatsMap.map.model.service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kh.eatsMap.common.code.Category;
import com.kh.eatsMap.common.code.HashCode;
import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.map.model.dto.Map;
import com.kh.eatsMap.map.model.repository.MapRepository;
import com.kh.eatsMap.map.model.repository.myMapRepository;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.repository.FollowingRepository;
import com.kh.eatsMap.member.model.repository.MemberRepository;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.repository.FileRepository;
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
	
	@Autowired
	private final MemberRepository memberRepository;
	
	@Autowired
	private final FileRepository fileRepository;

	@Override
	public List<HashMap<String, Object>> reviewList() {
		List<HashMap<String, Object>> mapList = new ArrayList<>();

		List<Review> reviews = mapRepository.findAll();
		
		
		for (Review review : reviews) {
			HashMap<String, Object> hashmap = new HashMap<>();
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
	        if(files.size() > 0) {
	        	review.setThumUrl(files.get(0).getDownloadURL());
	        }
	        review.setCategory(converterCategory(review.getCategory()));
	        review.setHashtag(converterHashtag(review.getHashtag()));
			hashmap.put("review", review);
			hashmap.put("reviewId", review.getId().toString());
			mapList.add(hashmap);
		}
		
		return mapList;

	}

	@Override
	public List<Follow> findFollowList(ObjectId member_id) {
		// TODO Auto-generated method stub

		List<Follow> follows = followingRepository.findByMemberId(member_id).orElse(List.of());

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
		
		
		reviews.addAll(followReview);
		

		for (Review review : reviews) {
			HashMap<String, Object> hashmap = new HashMap<>();
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
	        if(files.size() > 0) {
	        	review.setThumUrl(files.get(0).getDownloadURL());
	        }
	        review.setCategory(converterCategory(review.getCategory()));
	        review.setHashtag(converterHashtag(review.getHashtag()));
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

	@Override
	public List<HashMap<String, Object>> findMemberList(String groupId) {
		
		List<HashMap<String, Object>> memberList = new ArrayList<>();
		
		ObjectId grid = new ObjectId(groupId);

		List<Group> groups = groupRepository.findById(grid);
		
		System.out.println(groups);
		Member member = new Member();
		
		for (Group group : groups) {
			ObjectId[] ObjectArr = group.getParticipants();
			for (int i = 0; i < group.getParticipants().length; i++) {
				HashMap<String, Object> hashmap = new HashMap<>();
				member = memberRepository.findById(ObjectArr[i]);	
				hashmap.put("memberName", member.getNickname());
				hashmap.put("memberId", member.getId().toString());
				memberList.add(hashmap);
				
			}
			System.out.println(memberList);	
			
		}
		
		return memberList;
	}

	@Override
	public List<HashMap<String, Object>> findGroupReview(String groupId) {
		List<HashMap<String, Object>> groupReview = new ArrayList<>();
		
		List<Review> reviews = mapRepository.findByGroup(groupId);
		for (Review review : reviews) {
			HashMap<String, Object> hashmap = new HashMap<>();
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
	        if(files.size() > 0) {
	        	review.setThumUrl(files.get(0).getDownloadURL());
	        }
	        review.setCategory(converterCategory(review.getCategory()));
	        review.setHashtag(converterHashtag(review.getHashtag()));
			hashmap.put("review", review);
			hashmap.put("reviewId", review.getId().toString());
			groupReview.add(hashmap);
		}
		return groupReview;
	}

	@Override
	public List<HashMap<String, Object>> findByGroupIdAndMemberId(String groupId, String memberId) {
		List<HashMap<String, Object>> groupMemberReview = new ArrayList<>();
		ObjectId id = new ObjectId(groupId);
		
		List<Review> reviews = mapRepository.findByGroupAndMemberId(id,memberId);
		for (Review review : reviews) {
			HashMap<String, Object> hashmap = new HashMap<>();
			List<Fileinfo> files = fileRepository.findByTypeId(review.getId());
	        if(files.size() > 0) {
	        	review.setThumUrl(files.get(0).getDownloadURL());
	        }
	        
			hashmap.put("review", review.toString());
			hashmap.put("reviewId", review.getId().toString());
			groupMemberReview.add(hashmap);
		}
		
		return groupMemberReview;
	}
	
	public String converterCategory(String category) {
		switch (category) {
		case "cg01": category = Category.CG01.desc(); break;
		case "cg02": category = Category.CG02.desc(); break;
		case "cg03": category = Category.CG03.desc(); break;
		case "cg04": category = Category.CG04.desc(); break;
		case "cg05": category = Category.CG05.desc(); break;
		case "cg06": category = Category.CG06.desc(); break;
		case "cg07": category = Category.CG07.desc(); break;
		case "cg08": category = Category.CG08.desc(); break;
		default:
			break;
		}
		
		return category;
	}
	
	public String[] converterHashtag(String[] hashtag) {
		for (int i = 0; i < hashtag.length; i++) {
			switch (hashtag[i]) {
			case "md01": hashtag[i] = HashCode.MD01.desc(); break;
			case "md02": hashtag[i] = HashCode.MD02.desc(); break;
			case "md03": hashtag[i] = HashCode.MD03.desc(); break;
			case "md04": hashtag[i] = HashCode.MD04.desc(); break;
			case "md05": hashtag[i] = HashCode.MD05.desc(); break;
			case "md06": hashtag[i] = HashCode.MD06.desc(); break;
			case "pr01": hashtag[i] = HashCode.PR01.desc(); break;
			case "pr02": hashtag[i] = HashCode.PR02.desc(); break;
			case "pr03": hashtag[i] = HashCode.PR03.desc(); break;
			case "pr04": hashtag[i] = HashCode.PR04.desc(); break;
			case "pr05": hashtag[i] = HashCode.PR05.desc(); break;
			default:
				break;
			}			
		}
		return hashtag;

	}


}
