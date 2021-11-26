package com.kh.eatsMap.myeats.model.dao;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.myeats.model.dto.FindCriteria;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.dto.PageObject;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;

@Repository
public class GroupDAO {
	
	private static GroupRepository groupRepository;
	
	 @Autowired
	 private MongoTemplate mongoTemplate;
	 
	 @Autowired
	 private static Group group;
	 
	 @Autowired
	 private static Member member;
	 
		
		//검색기능
		public List<Member> listFind(FindCriteria findCri) throws Exception{
			System.out.println("GroupDAO.listFind()");
			List<Member> list = null;
			String tagName = "닉넹";
			String tagName2 = tagName;
			tagName2 = findCri.getKeyword();
			
			Query query = new Query();
			query = query.with(Sort.by(Sort.Direction.DESC,"id"));
			query.skip((findCri.getPage()-1) * findCri.getPerPageNum());
			query.limit((int)findCri.getPerPageNum());
			//query.addCriteria(Criteria.where("nickname").regex(tagName));
			
			list = mongoTemplate.find(query,com.kh.eatsMap.member.model.dto.Member.class,"member");
			
			return list;
		}
		//검색기능
		public int findCountData(FindCriteria findCri) throws Exception{
			System.out.println("GroupDAO.findCountData()");
			return (int)mongoTemplate.count(new Query(), "member"); 
		}
		
		//닉네임 리스트 멤버
		public List<Member> listMemberFindByNickName(String nickname) throws Exception{
			System.out.println("GroupDAO.listMemberFindByNickName()");
			List<Member> list = null;
			
			Query query = new Query();
			query = query.addCriteria(Criteria.where("nickname").is(nickname));
			
			list = mongoTemplate.find(query,com.kh.eatsMap.member.model.dto.Member.class,"member");
			
			return list;
		}
		
	 
	
	//페이징 및 조회/group.jsp
	public List<Group> list(PageObject pageObject) {
		System.out.println("GroupDAO.list()");
		
		//페이징 쿼리
		//db.group.find().sort({_id:-1}).skip((page-1) * perPageNum).limit(perPageNum)
		
		List<Group> list = null;
		//findAll된 자료에서 원하는 데이터만 가져오기 위한 Query객체 생성 - 조건이 없는 쿼리 객체 생성
		Query query = new Query();
		//groupIdx로 최근순 정렬
		//id로 잡으면 알아서 최신순 정렬됨
		query = query.with(Sort.by(Sort.Direction.DESC,"id"));
		//이전 페이지의 데이터는 skip 시킨다.
		query.skip((pageObject.getPage()-1) * pageObject.getPerPageNum());
		//한 페이지에 표시할 데이터만큼만 가져오기
		//limit이 int만 받음
		query.limit((int)pageObject.getPerPageNum());
		//데이터를 가져와서 list에 채운다.
		list = mongoTemplate.find(query,com.kh.eatsMap.myeats.model.dto.Group.class,"group");
		
		//System.out.println("GroupDAO.list().list : " + list);
		return list;
	}
	
	//전체 게시글의 개수:조회 기능에 사용
	public Integer getTotalCount() {
		System.out.println("GroupDAO.getTotalCount()");
		//new Query()(조건없는 쿼리)
		//group테이블에서 조건 없는 쿼리를 통해 모든 데이터를 꺼내와 개수를 헤아림
		return (int)mongoTemplate.count(new Query(), "group"); 
	}
	
	
	
	//수정하기에 쓰인 update(미완)
		public void update(Group group) throws Exception{
			Query query = new Query();
			Update update = new Update();
			//query.addCriteria(Criteria.where("컬럼명1").is("조건값1"));
			query.addCriteria(Criteria.where("id").is("id"));
			//추가필요 update.set("컬럼명1", "변경값1");
			update.set("groupName", group.getGroupName());
			mongoTemplate.updateMulti(query, update, "group");
		}
	
	
}
