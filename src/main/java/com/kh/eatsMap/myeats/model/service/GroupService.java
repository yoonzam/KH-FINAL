package com.kh.eatsMap.myeats.model.service;


import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.common.util.FindCriteria;
import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface GroupService {

	//회원 검색
	public List<Member> listMemberFind(FindCriteria findCri) throws Exception;
	public int findMemberCountData(FindCriteria findCri) throws Exception;
	
	//닉네임으로 부르기
	public List<Member> listMemberFindByNickName(String nickname) throws Exception;
	
	
	//페이징 및 조회/group.jsp
	public List<Group> list(PageObject pageObject, Member member);
	
	//groupIdx제외 예정
	public List<Group> read(ObjectId id);

	//파일 업로드 및 write
	void write(Group group, List<MultipartFile> photos, Member member);
	
	//파일 업로드
	List<Fileinfo> findFiles(ObjectId id);
	
	public void remove(String id);
	
	public void modify(Group group,List<MultipartFile> photos, Member member,
			ObjectId delNickNameOne,ObjectId delNickNameTwo,ObjectId delNickNameThree,
			ObjectId delNickNameFour,ObjectId delNickNameFive,ObjectId delNickNameSix,
			ObjectId newNickNameOne,ObjectId newNickNameTwo,ObjectId newNickNameThree,
			ObjectId newNickNameFour,ObjectId newNickNameFive,ObjectId newNickNameSix) throws Exception;
	
	public Member findMemberById(ObjectId id);
	 
	public  List<Member> findMember() throws Exception;
	
	public  List<Member> findMemberlistById(ObjectId id) throws Exception;
	
	public Group findGroupById(ObjectId id) throws Exception;
	
	//회원의 그룹나가기
	public void groupLeave(Group group,ObjectId id) throws Exception;
	
	//Post Paging 처리
	Map<String,Object> findMemberAndReviewByMemberIdPage(PageObject pageObject,ObjectId memberId);
	Integer getTotalCount(); 
	Integer getTotalCountBymemberId(ObjectId memberId); 
	
	Integer getTotalCountGroupBymemberId(ObjectId memberId); 
}
