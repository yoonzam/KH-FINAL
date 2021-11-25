package com.kh.eatsMap.myeats.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.myeats.model.dto.FindCriteria;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.dto.PageObject;
import com.kh.eatsMap.myeats.model.service.GroupService;

@Controller
@RequestMapping("/myeats/*")
public class MyeatsController {

	private static final Logger logger = LoggerFactory.getLogger(MyeatsController.class);
	
	@Inject
	private GroupService groupService;
	
	@Inject
	private MemberService memberService;
	
	
	@RequestMapping(value="/invite", method=RequestMethod.GET)
	public void list(@ModelAttribute("fCri") FindCriteria fCri, Model model) throws Exception{
		logger.info(fCri.toString());
//		model.addAttribute("list", groupService.listMember(fCri));
		model.addAttribute("list", groupService.listMemberFind(fCri));
		
		
		PageObject pageObject = new PageObject();
		pageObject.setPage(fCri.getPage());
		pageObject.setPerPageNum(fCri.getPerPageNum());
		
		pageObject.setTotalRow(groupService.findMemberCountData(fCri));
		
		
		model.addAttribute("pageObject", pageObject);		
	}
	
	
	@RequestMapping(value="/invite", method=RequestMethod.POST)
	public String invitePost(@RequestParam("keyword") String keyword, Model model)throws Exception{ 
		
		
		System.out.println(keyword);
		
		return "redirect:/myeats/createGroup";
	}
	
	
	
	
	//페이징 및 조회/group.jsp
	@RequestMapping(value="/group", method=RequestMethod.GET)
	public void groupGet(Model model, PageObject pageObject) throws Exception{
		logger.info("groupGet.............");
		
		model.addAttribute("list", groupService.list(pageObject));
		model.addAttribute("pageObject", pageObject);
	}
	
	//그룹생성 폼/createGroup.jsp
	@RequestMapping(value="/createGroup", method = RequestMethod.GET)
	public void createGroupGet(Group group, Model model) throws Exception{
		logger.info("createGroupGet.....");
		
		model.addAttribute("group",memberService.findMemberByNickname("geoTest1").getNickname());
		System.out.println(memberService.findMemberByNickname("geoTest1").toString());
	}
	
	//그룹생성 처리/createGroup.jsp
	@RequestMapping(value="/createGroup", method = RequestMethod.POST)
	public String writePost(Group group, RedirectAttributes reAttr, PageObject pageObject) throws Exception{
		logger.info("writePost....");
		logger.info(group.toString());
		
		groupService.write(group);
		reAttr.addFlashAttribute("list", groupService.list(pageObject));
		reAttr.addFlashAttribute("result", "success");
		
		return "redirect:/myeats/group";
	}
	
	//그룹상세보기 폼/groupDetail.jsp
	@RequestMapping(value="/groupDetail", method=RequestMethod.GET)
	public void groupDetailGet(@RequestParam("id") ObjectId id, Model model) throws Exception{
		
		model.addAttribute("groupService",groupService.read(id)); 
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("id") String id, RedirectAttributes reAttr)throws Exception{ 
		
		groupService.remove(id);
		System.out.println(id);
		reAttr.addFlashAttribute("result", "success");	
		
		return "redirect:/myeats/group";
	}
	
	//수정처리
		@RequestMapping(value="/groupDetailModify", method=RequestMethod.GET)
		public void modifyGet(@RequestParam("id") ObjectId id, Model model) throws Exception{
			logger.info("modifyGET()........");
			model.addAttribute("groupService",groupService.read(id)); 
		}
		
//		@RequestMapping(value="/groupDetailModify", method=RequestMethod.POST)
//		public String modifyPOST(Group group, RedirectAttributes reAttr) throws Exception{
//			logger.info("modifyPOST()........");
//			groupService.modify(group);
//			
//			return "redirect:/myeats/group";
//		}
	


	@GetMapping("/post")
	public String postView() {
		return "myeats/post";
	}
	@GetMapping("/detail")
	public String detailView() {
		return "myeats/detail";
	}
	
}
