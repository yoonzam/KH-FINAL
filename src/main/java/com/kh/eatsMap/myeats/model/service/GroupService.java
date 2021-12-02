package com.kh.eatsMap.myeats.model.service;


import java.util.List;

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
	
	public void modify(Group group,List<MultipartFile> photos, Member member,String delNickName,ObjectId newNickNameOne) throws Exception;
	
	public Member findMemberById(ObjectId id);
	 
	public  List<Member> findMember() throws Exception;
	
	
}
