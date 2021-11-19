package com.kh.eatsMap.myeats.controller;

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

import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.service.GroupService;

@Controller
@RequestMapping("/myeats/*")
public class MyeatsController {

	private static final Logger logger = LoggerFactory.getLogger(MyeatsController.class);
	
	@Inject
	private GroupService groupService;
	
	@RequestMapping(value="/createGroup", method = RequestMethod.GET)
	public void createGroupGet(Group group, Model model) throws Exception{
		logger.info("createGroupGet.....");
	}
	
	@RequestMapping(value="/createGroup", method = RequestMethod.POST)
	public String writePost(Group group, RedirectAttributes reAttr) throws Exception{
		logger.info("writePost....");
		logger.info(group.toString());
		
		groupService.write(group);
		reAttr.addFlashAttribute("result", "success");
		
		return "redirect:/myeats/group";
	}
	
	@RequestMapping(value="/group", method=RequestMethod.GET)
	public void groupGet(Model model) throws Exception{
		logger.info("groupGet.............");
		
		model.addAttribute("list", groupService.list());
	}
	
	@RequestMapping(value="/groupDetail", method=RequestMethod.GET)
	public void groupDetailGet(@RequestParam("groupIdx") int groupIdx, Model model) throws Exception{
		
		model.addAttribute("groupService",groupService.read(groupIdx)); 
	}

	@GetMapping("/post")
	public String postView() {
		return "myeats/post";
	}
	@GetMapping("/detail")
	public String detailView() {
		return "myeats/detail";
	}
	
}
