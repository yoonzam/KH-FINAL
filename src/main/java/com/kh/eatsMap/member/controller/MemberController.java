package com.kh.eatsMap.member.controller;

import java.util.UUID;

import javax.mail.Multipart;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.eatsMap.common.code.ErrorCode;
import com.kh.eatsMap.common.exception.HandlableException;
import com.kh.eatsMap.common.validator.ValidatorResult;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.JoinForm;

@Controller
@RequestMapping("member")
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		super();
		this.memberService = memberService;
	}

	@GetMapping("login")
	public void login() {}
	
	@PostMapping("login")
	public String loginImpl(Member member, HttpSession session
							,RedirectAttributes redirectAttr) {
		Member certifiedUser = memberService.authenticateUser(member);
		memberService.findMember().forEach(e -> logger.debug(e.toString()));
		
		if(certifiedUser == null) {
			redirectAttr.addFlashAttribute("message", "아이디나 비밀번호가 틀렸습니다.");
			return "redirect:/member/login";
		}
		
		session.setAttribute("authentication", certifiedUser);
		return "redirect:/main/";
	}
	
	@GetMapping("join-impl/{token}")
	public String joinImpl(@PathVariable String token
						,@SessionAttribute(value = "persistToken", required = false) String persistToken
						,@SessionAttribute(value = "persistUser", required = false) JoinForm persistUser
						,HttpSession session
						,RedirectAttributes redirectAttr
			) {
		
		if(!token.equals(persistToken)) {
			throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
		}
		//memberService.findMember().forEach(e -> logger.debug(e.toString()));
		memberService.insertMember(persistUser);
		
		session.removeAttribute("persistToken");
		session.removeAttribute("persistUser");
		redirectAttr.addFlashAttribute("message", "환영합니다. 고객님");
		return "redirect:/member/login";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authentication");
		
		return "redirect:/member/login";
	}
	
	@GetMapping("find-password")
	public void findPassword() {}
	
	@PostMapping("tmp-password")
	public String sendTmpPassword(@Validated EmailForm form
								, Errors errors, Model model, RedirectAttributes redirectAttr) {
		//존재하는 이메일인지 검증
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error",vr.getError());
		
		if(errors.hasErrors()) {
			vr.addErrors(errors);
			return "member/find-password";
		}
		
		//임시비밀번호 발급
		String tmpPassword = UUID.randomUUID().toString().substring(0, 7);
		memberService.sendTmpPassword(form);
	
		return "redirect:/main/";
	}
	
	@GetMapping("join")
	public void join() {}
	
	@PostMapping("join")
	public String joinImpl(@Validated JoinForm form
						,Errors errors
						,Model model
						,HttpSession session
						,RedirectAttributes redirectAttr) {
		
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error",vr.getError());
		
		if(errors.hasErrors()) {
			vr.addErrors(errors);
			return "member/join";
		}
		
		String token = UUID.randomUUID().toString();
		session.setAttribute("persistUser", form);
		session.setAttribute("persistToken", token);
		
		memberService.authenticateByEmail(form,token);
		
		return "redirect:/main/";
	}
	
	@GetMapping("kakao-join")
	public void kakaoJoin() {}
	
	@GetMapping("edit-profile")
	public void editProfile() {}
	
	@PostMapping("update-img")
	public String updateImg(MultipartFile profile, @SessionAttribute("authentication") Member member) {
		logger.debug(profile.toString());
		
		memberService.insertProfileImg(member,profile);
		
		return "redirect:/member/edit-profile";
	}

}
