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
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.service.GroupService;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.service.TimelineService;


@Controller
@RequestMapping("myeats")
public class MyeatsController {

	private static final Logger logger = LoggerFactory.getLogger(MyeatsController.class);
	
	@Inject
	private GroupService groupService;
	
	@Inject
	private MemberService memberService;
	
	@Inject
	private TimelineService timelineService;
	
	
	@RequestMapping(value="/invite", method=RequestMethod.GET)
	public void list(@ModelAttribute("fCri") FindCriteria fCri, Model model) throws Exception{
		logger.info(fCri.toString());
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
	public void groupGet(Model model, PageObject pageObject) throws Exception{
		logger.info("groupGet.............");
		List<Group> groups = groupService.list(pageObject);
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
		logger.info("createGroupGet.....");
		
		model.addAttribute("group",memberService.findMemberByNickname("알파카").getNickname());
		//System.out.println(memberService.findMemberByNickname("geoTest1").toString());
	}
	

	
	//그룹생성 처리/createGroup.jsp
	@RequestMapping(value="/createGroup", method = RequestMethod.POST)
	public String writePost(Group group, RedirectAttributes reAttr, PageObject pageObject,List<MultipartFile> photos, Member member) throws Exception{
		logger.info("writePost....");
		logger.info(group.toString());
		
		groupService.write(group,photos,member);
		reAttr.addFlashAttribute("list", groupService.list(pageObject));
		reAttr.addFlashAttribute("result", "success");
		
		return "redirect:/myeats/group";
	}
	
	//그룹상세보기 폼/groupDetail.jsp
	@RequestMapping(value="/groupDetail", method=RequestMethod.GET)
	public void groupDetailGet(@RequestParam("id") ObjectId id, Model model) throws Exception{
		List<Group> groups = groupService.read(id);
		for (Group group : groups) {
			List<Fileinfo> files = groupService.findFiles(group.getId());
			if(files.size() > 0) group.setThumUrl(files.get(0).getDownloadURL());
		}
		model.addAttribute("groups", groups);
		
		
		
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("id") String id, RedirectAttributes reAttr)throws Exception{ 
		
		groupService.remove(id);
		System.out.println(id);
		reAttr.addFlashAttribute("result", "success");	
		
		return "redirect:/myeats/group";
	}
	
	//수정조회
	@RequestMapping(value="/groupDetailModify", method=RequestMethod.GET)
	public void modifyGet(@RequestParam("id") ObjectId id, Model model) throws Exception{
		logger.info("modifyGET()........");
		
		List<Group> groups = groupService.read(id);
		for (Group group : groups) {
			List<Fileinfo> files = groupService.findFiles(group.getId());
			if(files.size() > 0) group.setThumUrl(files.get(0).getDownloadURL());
		}
		model.addAttribute("groups", groups);
		
	}
	//수정처리
	@RequestMapping(value="/groupDetailModify", method=RequestMethod.POST)
	public String modifyPOST(Group group,List<MultipartFile> photos, Member member) throws Exception{
		logger.info("modifyPOST()........");
		groupService.modify(group,photos,member);
		
		
		return "redirect:/myeats/groupDetail?id="+group.getId();
	}
	


//	@RequestMapping(value="/post", method=RequestMethod.GET)
//	public String postList(Model model) {
//		logger.info("postGET()........");
//		model.addAttribute("allReviews",timelineService.findAllReviews()); 
//		return "myeats/post";
//	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)	
	public String detailList(Model model) {
		logger.info("detaiGET()........");
		model.addAttribute("allReviews",timelineService.findAllReviews()); 
		return "myeats/detail";
	}
		
	//유진 11/30
	@GetMapping("post")
	public void group(@SessionAttribute("authentication") Member member,Model model) {
		model.addAllAttributes(memberService.findMemberAndReviewByMemberId(member.getId().toString()));
	}
	



}

