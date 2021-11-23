package com.kh.eatsMap.myeats.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.service.MemberService;
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
	
	@RequestMapping(value="/createGroup", method = RequestMethod.GET)
	public void createGroupGet(Group group, Model model) throws Exception{
		logger.info("createGroupGet.....");
		
		
		model.addAttribute("group",memberService.findMemberByNickname("geoTest1").getNickname());
		System.out.println(memberService.findMemberByNickname("geoTest1").toString());
		
		
		
	}
	
	@RequestMapping(value="/createGroup", method = RequestMethod.POST)
	public String writePost(Group group, RedirectAttributes reAttr, PageObject pageObject) throws Exception{
		logger.info("writePost....");
		logger.info(group.toString());
		
		groupService.write(group);
		reAttr.addFlashAttribute("list", groupService.list(pageObject));
		reAttr.addFlashAttribute("result", "success");
		
		return "redirect:/myeats/group";
	}
	
//	@RequestMapping(value="/group", method=RequestMethod.GET)
//	public void groupGet(Model model) throws Exception{
//		logger.info("groupGet.............");
//		
//		model.addAttribute("list", groupService.list());
//	}
	
	//페이징
	@RequestMapping(value="/group", method=RequestMethod.GET)
	public void groupGet(Model model, PageObject pageObject) throws Exception{
		logger.info("groupGet.............");
		
		model.addAttribute("list", groupService.list(pageObject));
		model.addAttribute("pageObject", pageObject);
	}
	
	@RequestMapping(value="/groupDetail", method=RequestMethod.GET)
	public void groupDetailGet(@RequestParam("groupIdx") String groupIdx, Model model) throws Exception{
		
		model.addAttribute("groupService",groupService.read(groupIdx)); 
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
		public void modifyGet(@RequestParam("groupIdx") String groupIdx, Model model) throws Exception{
			logger.info("modifyGET()........");
			model.addAttribute("groupService",groupService.read(groupIdx)); 
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
