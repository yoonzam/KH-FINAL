package com.kh.eatsMap.myeats.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.eatsMap.common.util.Fileinfo;
import com.kh.eatsMap.common.util.FindCriteria;
import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.firebase.PushMessaging;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.service.GroupService;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.service.TimelineService;


@Controller
@RequestMapping("myeats")
public class MyeatsController {

	private static final Logger logger = LoggerFactory.getLogger(MyeatsController.class);
	private PushMessaging push = PushMessaging.getInstance();
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TimelineService timelineService;
	
	
	@RequestMapping(value="/invite", method=RequestMethod.GET)
	public void list(@ModelAttribute("fCri") FindCriteria fCri, Model model) throws Exception{
		model.addAttribute("list", groupService.listMemberFind(fCri));
		
		PageObject pageObject = new PageObject();
		pageObject.setPage(fCri.getPage());
		pageObject.setPerPageNum(fCri.getPerPageNum());
		
		pageObject.setTotalRow(groupService.findMemberCountData(fCri));
		
		
		model.addAttribute("pageObject", pageObject);		
	}
	
	//test
	@RequestMapping(value="/invite", method=RequestMethod.POST)
	public String invitePost(HttpServletRequest request, Model model,RedirectAttributes rttr)throws Exception{ 
		
		String keyword = request.getParameter("keyword");
		//http://localhost:7979/myeats/createGroup
		 rttr.addFlashAttribute("keyword", keyword);
		//http://localhost:7979/myeats/invite?page=1&numPerPage=10&findType=S&keyword=%EB%8C%95%EB%8C%95%EC%9D%B4
		//http://localhost:7979/myeats/createGroup?keyword=알파카%2C알파카%2C알파카
		
		return  "redirect:/myeats/createGroup";
	}

	
	@RequestMapping(value="/group", method=RequestMethod.GET)
	public void groupGet(Model model, PageObject pageObject,@SessionAttribute("authentication") Member member) throws Exception{
		List<Group> groups = groupService.list(pageObject,member);
		for (Group group : groups) {
			List<Fileinfo> files = groupService.findFiles(group.getId());
			if(files.size() > 0) group.setThumUrl(files.get(0).getDownloadURL());
		}
		model.addAttribute("groups", groups);
		
		//model.addAttribute("list", groupService.list(pageObject));
		model.addAttribute("pageObject", pageObject);
	}
	
	//그룹생성 폼/createGroup.jsp
	@RequestMapping(value="/createGroup", method = RequestMethod.GET)
	public void createGroupGet(Group group, Model model, String memberNickName) throws Exception{
		model.addAttribute("group",memberService.findMemberByNickname("알파카").getNickname());
		//System.out.println(memberService.findMemberByNickname("geoTest1").toString());
	}
	
	//그룹생성 처리/createGroup.jsp
//	@RequestMapping(value="/createGroup", method = RequestMethod.POST)
//	public String writePost(Group group, RedirectAttributes reAttr, PageObject pageObject,List<MultipartFile> photos, @SessionAttribute("authentication") Member member) throws Exception{
//		
//		groupService.write(group,photos,member);
//		reAttr.addFlashAttribute("list", groupService.list(pageObject,member));
//		reAttr.addFlashAttribute("result", "success");
//		
//		return "redirect:/myeats/group";
//	}
	
	//그룹상세보기 폼/groupDetail.jsp
	@RequestMapping(value="/groupDetail", method=RequestMethod.GET)
	public void groupDetailGet(@RequestParam("id") ObjectId id, Model model) throws Exception{
		List<Group> groups = groupService.read(id);
		for (Group group : groups) {
			List<Fileinfo> files = groupService.findFiles(group.getId());
			if(files.size() > 0) group.setThumUrl(files.get(0).getDownloadURL());
		}
		//Groupid로 Member nickName찾기
		Group currentGroup = groupService.findGroupById(id);
		ObjectId[] currentParticipants = currentGroup.getParticipants();
		Member member = new Member();
		String nickName = null;
		List<String> nickNames =  new ArrayList<String>(); 
		int i = 0;
			for (ObjectId currentParticipant : currentParticipants) {
				member = groupService.findMemberById(currentParticipant);
				nickName = member.getNickname(); 
				nickNames.add(i,nickName);
				i++;
			}
			 Map<String,Object> map = new HashMap<String,Object>();
				map = Map.of("groups", groups, "nickNames", nickNames);
			
			model.addAllAttributes(map);
	}
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteGet(@RequestParam("id") String id, RedirectAttributes reAttr)throws Exception{ 
		
		groupService.remove(id);
		reAttr.addFlashAttribute("result", "success");	
		
		return "redirect:/myeats/group";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String deletePost(@RequestParam("id") String id, RedirectAttributes reAttr)throws Exception{ 
		
		groupService.remove(id);
		reAttr.addFlashAttribute("result", "success");	
		
		return "redirect:/myeats/group";
	}
	
	//수정조회
	@RequestMapping(value="/groupDetailModify", method=RequestMethod.GET)
	public void modifyGet(@RequestParam("id") ObjectId id, Model model) throws Exception{
		List<Group> groups = groupService.read(id);
		for (Group group : groups) {
			List<Fileinfo> files = groupService.findFiles(group.getId());
			if(files.size() > 0) group.setThumUrl(files.get(0).getDownloadURL());
		}
//		model.addAttribute("groups", groups);
		
		//Groupid로 Member nickName찾기
				Group currentGroup = groupService.findGroupById(id);
				ObjectId[] currentParticipants = currentGroup.getParticipants();
				Member member = new Member();
				String nickName = null;
				List<String> nickNames =  new ArrayList<String>(); 
				int i = 0;
					for (ObjectId currentParticipant : currentParticipants) {
						member = groupService.findMemberById(currentParticipant);
						nickName = member.getNickname(); 
						nickNames.add(i,nickName);
						i++;
					}
					 Map<String,Object> map = new HashMap<String,Object>();
						map = Map.of("groups", groups, "nickNames", nickNames);
					
					model.addAllAttributes(map);
	}
	
	//수정처리
		@RequestMapping(value="/groupDetailModify", method=RequestMethod.POST)
		public String modifyPOST(Group group,
				List<MultipartFile> photos, Member member,
				@RequestParam(value="delNickNameOne", required = false) ObjectId delNickNameOne,
				@RequestParam(value="delNickNameTwo", required = false) ObjectId delNickNameTwo,
				@RequestParam(value="delNickNameThree", required = false) ObjectId delNickNameThree,
				@RequestParam(value="delNickNameFour", required = false) ObjectId delNickNameFour,
				@RequestParam(value="delNickNameFive", required = false) ObjectId delNickNameFive,
				@RequestParam(value="delNickNameSix", required = false) ObjectId delNickNameSix,
				@RequestParam(value ="newNickNameOne",required = false)ObjectId newNickNameOne) throws Exception{
			//System.out.println(photos);
			System.out.println(delNickNameOne+ ","+ delNickNameTwo+ ","+delNickNameThree+ ","
					+ delNickNameFour+ ","+ delNickNameFive+ ","+ ","+ delNickNameSix
					+newNickNameOne+ ","+ group+ ","+ member);
			groupService.modify(group,photos,member,
					delNickNameOne,delNickNameTwo,delNickNameThree,
					delNickNameFour,delNickNameFive,delNickNameSix,
					newNickNameOne);
			return "redirect:/myeats/groupDetail?id="+group.getId();
		}
	
		
	//유진 11/30
	@GetMapping("post")
	public void group(@SessionAttribute("authentication") Member member,Model model) {
		model.addAllAttributes(memberService.findMemberAndReviewByMemberId(member.getId()));
	}
	
	@GetMapping("detail")
	public void likedReview(@SessionAttribute("authentication") Member member, Model model) {
		model.addAttribute("reviews",memberService.findLikedByMemberId(member));
	}
	
   //유진 12/02
   @PostMapping("createGroup")
   public String createGroup(Group group, PageObject pageObject,List<MultipartFile> photos
                     , @SessionAttribute("authentication") Member member, RedirectAttributes reAttr ) {
     
	   //System.out.println(photos);
	   groupService.write(group,photos,member);
      
      for (int i = 0; i < group.getParticipants().length; i++) {
    	  Member to = memberService.findMemberById(group.getParticipants()[i]);
    	  Notice notice = memberService.findNotice(to.getId());
    	  memberService.updateNotice("group", notice);
    	  push.push(to);
      }
      //System.out.println(photos);
      reAttr.addFlashAttribute("list", groupService.list(pageObject,member));
      reAttr.addFlashAttribute("result", "success");
      return "redirect:/myeats/group";
   }



}

