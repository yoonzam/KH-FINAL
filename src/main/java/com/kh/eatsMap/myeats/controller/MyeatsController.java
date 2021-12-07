package com.kh.eatsMap.myeats.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.kh.eatsMap.common.code.ErrorCode;
import com.kh.eatsMap.common.exception.HandlableException;
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
		 rttr.addFlashAttribute("keyword", keyword);
	
		return  "redirect:/myeats/createGroup";
	}

	
	@RequestMapping(value="/group", method=RequestMethod.GET)	//유진 12/06
	public void groupGet(Model model, PageObject pageObject,@SessionAttribute("authentication") Member member 
							,HttpSession session) throws Exception{
		
		Notice notice = memberService.findNoticeByMemberId(member.getId());
		int noticeCnt = notice.getCalendarNotice() + notice.getGroupNotice() + notice.getParticipantNotice() + notice.getFollowNotice();
		session.setAttribute("notice", notice);
		session.setAttribute("noticeCnt", noticeCnt);
		
		List<Group> groups = groupService.list(pageObject,member);
		for (Group group : groups) {
			List<Fileinfo> files = groupService.findFiles(group.getId());
			if(files.size() > 0) group.setThumUrl(files.get(0).getDownloadURL());
		}
		
		pageObject.setTotalRow(groupService.getTotalCountGroupBymemberId(member.getId()));
		model.addAttribute("groups", groups);
		
		model.addAttribute("pageObject", pageObject);
	}
	
	//그룹생성 폼/createGroup.jsp
	@RequestMapping(value="/createGroup", method = RequestMethod.GET)
	public void createGroupGet() {}
	
	
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
	public String modifyPOST(Group group
			,@RequestParam(name = "photos", required = false) MultipartFile photo, Member member,
			@RequestParam(value="delNickNameOne", required = false) ObjectId delNickNameOne,
			@RequestParam(value="delNickNameTwo", required = false) ObjectId delNickNameTwo,
			@RequestParam(value="delNickNameThree", required = false) ObjectId delNickNameThree,
			@RequestParam(value="delNickNameFour", required = false) ObjectId delNickNameFour,
			@RequestParam(value="delNickNameFive", required = false) ObjectId delNickNameFive,
			@RequestParam(value="delNickNameSix", required = false) ObjectId delNickNameSix,
			@RequestParam(value ="newNickNameOne",required = false)ObjectId newNickNameOne,
			@RequestParam(value ="newNickNameTwo",required = false)ObjectId newNickNameTwo,
			@RequestParam(value ="newNickNameThree",required = false)ObjectId newNickNameThree,
			@RequestParam(value ="newNickNameFour",required = false)ObjectId newNickNameFour,
			@RequestParam(value ="newNickNameFive",required = false)ObjectId newNickNameFive,
			@RequestParam(value ="newNickNameSix",required = false)ObjectId newNickNameSix) throws Exception{
		groupService.modify(group,photo,member,
				delNickNameOne,delNickNameTwo,delNickNameThree,
				delNickNameFour,delNickNameFive,delNickNameSix,
				newNickNameOne,newNickNameTwo,newNickNameThree,
				newNickNameFour,newNickNameFive,newNickNameSix);
		return "redirect:/myeats/groupDetail?id="+group.getId();
	}
		
	//그룹 나가기
	@RequestMapping(value="/groupLeave", method=RequestMethod.POST)
	public String groupLeavePost(Group group,@SessionAttribute("authentication") Member member)throws Exception{ 
		groupService.groupLeave(group,member.getId());
		return "redirect:/myeats/group";
	}	
	

	@GetMapping("post")
	public void group(@SessionAttribute("authentication") Member member,Model model, PageObject pageObject,@ModelAttribute("fCri") FindCriteria fCri) {
		
		model.addAllAttributes(groupService.findMemberAndReviewByMemberIdPage(pageObject,member.getId()));
		
		pageObject.setPage(fCri.getPage());
		pageObject.setPerPageNum(fCri.getPerPageNum());
		
		pageObject.setTotalRow(groupService.getTotalCountBymemberId(member.getId()));
		
		model.addAttribute("totalCount", groupService.getTotalCountBymemberId(member.getId()));	
		model.addAttribute("pageObject", pageObject);	
	}	
	@GetMapping("detail")
	public void likedReview(@SessionAttribute("authentication") Member member, Model model, PageObject pageObject, @ModelAttribute("fCri") FindCriteria fCri) {
		int cnt = memberService.countLikedReview(member);

		pageObject.setPage(fCri.getPage());
		pageObject.setPerPageNum(fCri.getPerPageNum());
		pageObject.setTotalRow(cnt);
		Pageable page = PageRequest.of((int)pageObject.getPage()-1, 8, Sort.by("id").descending());
		model.addAttribute("reviews",memberService.findLikedByMemberIdWithPage(page, member));
	}
	
   @PostMapping("createGroup")
   public String createGroup(Group group, PageObject pageObject,List<MultipartFile> photos
                     , @SessionAttribute("authentication") Member member, RedirectAttributes reAttr, Model model ) {
     
	   if(group.getParticipants() == null) {
		   throw new HandlableException(ErrorCode.NULL_OF_PARTICIPANT);
	   }
	   groupService.write(group,photos,member);
	   
      for (int i = 0; i < group.getParticipants().length; i++) {
    	  Member to = memberService.findMemberById(group.getParticipants()[i]);
    	  Notice notice = memberService.findNoticeByMemberId(to.getId());
    	  memberService.updateNotice("group", notice);
    	  push.push(to);
      }
      reAttr.addFlashAttribute("list", groupService.list(pageObject,member));
      reAttr.addFlashAttribute("result", "success");
      return "redirect:/myeats/group";
   }



}

