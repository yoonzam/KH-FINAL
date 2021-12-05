package com.kh.eatsMap.myeats.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.util.FileUtil;
import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.common.util.FindCriteria;
import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.repository.GroupRepository;
import com.kh.eatsMap.timeline.model.repository.FileRepository;
import com.kh.eatsMap.timeline.model.repository.TimelineRepository;

@Repository
public class GroupDAO {
	
	private static GroupRepository groupRepository;
	private static TimelineRepository timelineRepository;
	private static FileRepository fileRepository;
	
	 @Autowired
	 private MongoTemplate mongoTemplate;
	 
	 @Autowired
	 private static Group group;
	 
	 @Autowired
	 private static Member member;
	 
		
		//검색기능
		public List<Member> listFind(FindCriteria findCri) throws Exception{
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
			return (int)mongoTemplate.count(new Query(), "member"); 
		}
		
		//닉네임 리스트 멤버
		public List<Member> listMemberFindByNickName(String nickname) throws Exception{
			List<Member> list = null;
			
			Query query = new Query();
			query = query.addCriteria(Criteria.where("nickname").is(nickname));
			
			list = mongoTemplate.find(query,com.kh.eatsMap.member.model.dto.Member.class,"member");
			
			return list;
		}
		
		//전체 멤버 리스트
				public List<Member> listMember() throws Exception{
					List<Member> list = null;
					
					list = mongoTemplate.findAll(com.kh.eatsMap.member.model.dto.Member.class,"member");
					
					return list;
				}
				
				//id로 조회 멤버 리스트
				public List<Member> listMemberbyId(ObjectId id) throws Exception{
					List<Member> list = null;
					Query query = new Query();
					query = query.addCriteria(Criteria.where("id").is(id));
					list = mongoTemplate.find(query,com.kh.eatsMap.member.model.dto.Member.class,"member");
					
					return list;
				}
				
				
				 public Group findGroupById(ObjectId id){
				        Query query = new Query(Criteria.where("_id").is(id));

				        Group group = mongoTemplate.findOne(query,com.kh.eatsMap.myeats.model.dto.Group.class,"group");
				        return group;
				    }
				 
		
	 
	
	//페이징 및 조회/group.jsp
	public List<Group> list(PageObject pageObject,Member member) {
		
		//페이징 쿼리
		//db.group.find().sort({_id:-1}).skip((page-1) * perPageNum).limit(perPageNum)
		
		List<Group> list = null;
		//findAll된 자료에서 원하는 데이터만 가져오기 위한 Query객체 생성 - 조건이 없는 쿼리 객체 생성
		Query query = new Query();
		//groupIdx로 최근순 정렬
		//id로 잡으면 알아서 최신순 정렬됨
		query.addCriteria(Criteria.where("participants").in(member.getId()));
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
		//new Query()(조건없는 쿼리)
		//group테이블에서 조건 없는 쿼리를 통해 모든 데이터를 꺼내와 개수를 헤아림
		return (int)mongoTemplate.count(new Query(), "group"); 
	}
	
	
	

	
	public void update(Group group,List<MultipartFile> photos, Member member,
			ObjectId delNickNameOne,ObjectId delNickNameTwo,ObjectId delNickNameThree,
			ObjectId delNickNameFour,ObjectId delNickNameFive,ObjectId delNickNameSix,
			ObjectId newNickNameOne,ObjectId newNickNameTwo,ObjectId newNickNameThree,
			ObjectId newNickNameFour,ObjectId newNickNameFive,ObjectId newNickNameSix) throws Exception{
		
		//System.out.println("1"+photos);
		
		group.setMemberId(member.getId());
		
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(group.getId()));
		Update update = new Update();
		update.set("groupName", group.getGroupName());
		mongoTemplate.updateFirst(query, update, Group.class);
		
		//데이터를 제거
		//update.pull("participants", new ObjectId("619f82ad35d7987fdb82f440"));  
		update.pull("participants", delNickNameOne); 
		mongoTemplate.updateFirst(query, update, Group.class);
		update.pull("participants", delNickNameTwo); 
		mongoTemplate.updateFirst(query, update, Group.class);
		update.pull("participants", delNickNameThree); 
		mongoTemplate.updateFirst(query, update, Group.class);
		update.pull("participants", delNickNameFour); 
		mongoTemplate.updateFirst(query, update, Group.class);
		update.pull("participants", delNickNameFive); 
		mongoTemplate.updateFirst(query, update, Group.class);
		update.pull("participants", delNickNameSix); 
		mongoTemplate.updateFirst(query, update, Group.class);
		
		//데이터를 추가
//		 ObjectId[] newItem = new ObjectId[]{newNickNameOne,newNickNameTwo,newNickNameThree,
//					newNickNameFour,newNickNameFive,newNickNameSix};
//	    update = new Update();
//	    update.push("participants").each(newItem);
//	     mongoTemplate.updateFirst(query, update, Group.class);  
		
		//null값 잡기, null아니어도 다른문장 실행되어야 해서 따로 잡음
		if(newNickNameOne != null) {
			update = new Update();
		    update.push("participants").each(newNickNameOne);
		    mongoTemplate.updateFirst(query, update, Group.class);  
			}
		if(newNickNameTwo != null) {
			update = new Update();
		    update.push("participants").each(newNickNameTwo);
		    mongoTemplate.updateFirst(query, update, Group.class);  
			}
		if(newNickNameThree != null) {
			update = new Update();
		    update.push("participants").each(newNickNameThree);
		    mongoTemplate.updateFirst(query, update, Group.class);  
			}
		if(newNickNameFour != null) {
			update = new Update();
		    update.push("participants").each(newNickNameFour);
		    mongoTemplate.updateFirst(query, update, Group.class);  
			}
		if(newNickNameFive != null) {
			update = new Update();
		    update.push("participants").each(newNickNameFive);
		    mongoTemplate.updateFirst(query, update, Group.class);  
			}
		if(newNickNameSix != null) {
			update = new Update();
		    update.push("participants").each(newNickNameSix);
		    mongoTemplate.updateFirst(query, update, Group.class);  
			}
		
		 
	     


		Query fileDelQuery = new Query();
		//fileDelQuery.addCriteria(Criteria.where("typeId").in(new ObjectId("61a9da068c3f5568d074376e")));
		fileDelQuery.addCriteria(Criteria.where("typeId").is(group.getId()));
		mongoTemplate.remove(fileDelQuery, com.kh.eatsMap.common.util.Fileinfo.class);
	     
	     //fileRepository.deleteByTypeId(group.getId());
		
		FileUtil fileUtil = new FileUtil();
		for (MultipartFile photo : photos) {
			//System.out.println("2"+photo);
			//System.out.println("3"+!photo.isEmpty());
			
			if(!photo.isEmpty()) {
				Fileinfo fileInfo = fileUtil.fileUpload(photo);
				fileInfo.setTypeId(group.getId());
				mongoTemplate.save(fileInfo);
			}
		}
	    
	    

		
		}
	
	 public Member findMemberById(ObjectId id){
	        Query query = new Query(Criteria.where("_id").is(id));

	        Member member = mongoTemplate.findOne(query, Member.class, "member");
	        return member;
	    }
	
	
}
