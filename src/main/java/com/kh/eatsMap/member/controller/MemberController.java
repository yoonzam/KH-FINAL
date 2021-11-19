package com.kh.eatsMap.member.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.eatsMap.common.code.ErrorCode;
import com.kh.eatsMap.common.exception.HandlableException;
import com.kh.eatsMap.common.validator.ValidatorResult;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.EmailFormValidator;
import com.kh.eatsMap.member.validator.JoinForm;
import com.kh.eatsMap.member.validator.JoinFormValidator;
import com.kh.eatsMap.member.validator.ModifyForm;
import com.kh.eatsMap.member.validator.ModifyFormValidator;

@Controller
@RequestMapping("member")
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MemberService memberService;
	private JoinFormValidator joinFormValidator;
	private EmailFormValidator emailFormValidator;
	private ModifyFormValidator modifyFormValidator;
	
	public MemberController(MemberService memberService, JoinFormValidator joinFormValidator,
			EmailFormValidator emailFormValidator, ModifyFormValidator modifyFormValidator) {
		super();
		this.memberService = memberService;
		this.joinFormValidator = joinFormValidator;
		this.emailFormValidator = emailFormValidator;
		this.modifyFormValidator = modifyFormValidator;
	}

	//요청파라미터 값들을 바인드해줌
	@InitBinder(value = "joinForm")	//jsp form태그 modelAttribute value와 바인딩
	public void initBinderJoinForm(WebDataBinder webDataBinder) {	
		webDataBinder.addValidators(joinFormValidator);
	}
	@InitBinder(value = "emailForm")
	public void initBinderEmailForm(WebDataBinder webDataBinder) {	
		webDataBinder.addValidators(emailFormValidator);
	}
	@InitBinder(value = "modifyForm")
	public void initBinderModifyForm(WebDataBinder webDataBinder) {	
		webDataBinder.addValidators(modifyFormValidator);
	}


	@GetMapping("login")
	public void login() {}
	
	@PostMapping("login")
	public String loginImpl(Member member, HttpSession session
							,RedirectAttributes redirectAttr) {
		Member certifiedUser = memberService.authenticateUser(member);
		
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
		memberService.insertMember(persistUser);
		
		session.removeAttribute("persistToken");
		session.removeAttribute("persistUser");
		redirectAttr.addFlashAttribute("message", "환영합니다. 회원님");
		return "redirect:/member/login";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authentication");
		
		return "redirect:/member/login";
	}
	
	@GetMapping("find-password")
	public void findPassword(Model model) {
		model.addAttribute(new EmailForm()).addAttribute("error", new ValidatorResult().getError());
	}
	
	@PostMapping("find-password")
	public String sendTmpPassword(@Validated EmailForm form, Errors errors
								, Model model, RedirectAttributes redirectAttr) {
		
		//존재하는 이메일인지 검증
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error",vr.getError());
		
		if(errors.hasErrors()) {
			vr.addErrors(errors);
			return "member/find-password";
		}
		
		//임시비밀번호 발급
		String tmpPassword = UUID.randomUUID().toString().substring(0, 7);
		memberService.sendTmpPassword(form, tmpPassword);
		
		//임시비밀번호로 회원정보 업데이트
		memberService.updatePassword(form.getEmail(), tmpPassword);
	
		redirectAttr.addFlashAttribute("msg", tmpPassword);
		return "redirect:/member/find-password";
	}
	
	@GetMapping("join")
	public void join(Model model) {
		model.addAttribute(new JoinForm()).addAttribute("error", new ValidatorResult().getError());
	}
	
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
	
	@GetMapping("nickname-check")
	@ResponseBody
	public String nicknameCheck(String nickname) {
		Member member = memberService.findMemberByNickname(nickname);
		
		return (!nickname.equals("false") && member == null) ? "available" : "disabled";
	}
	
	@PostMapping("kakao-login")
	@ResponseBody
	public String kakaoLoginImpl( String kakaoId, HttpSession session) {
		Member member = memberService.findKakaoMember(kakaoId);
						
		if(member != null) {
			session.setAttribute("authentication", member);
			return "kakaoLogin";
		}else {
			return "kakaoJoin";
		}
	}
	
	@GetMapping("kakao-join")
	public void kakaoJoin() {}
	
	@PostMapping("kakao-join")
	public String kakaoJoinImpl(Member member) {
		
		memberService.saveMember(member);
		
		return "redirect:/member/login";
	}
	
	@GetMapping("edit-profile")
	public void editProfile(Model model) {
		model.addAttribute(new ModifyForm()).addAttribute("error", new ValidatorResult().getError());
	}
	
	@PostMapping("edit-profile")
	public String editProfileImpl(@Validated ModifyForm modifyForm, Errors errors
								,@SessionAttribute("authentication") Member member
								,Model model) {
		logger.debug(modifyForm.toString());
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error",vr.getError());		//Map<errorField, defaultMessage> 형태 -> jsp에서 error.field명 출력
		
		if(errors.hasErrors()) {
			vr.addErrors(errors);
			return "member/edit-profile";
		}
		memberService.updateMemberProfile(member,modifyForm);
		
		return "redirect:/myeats/post";
		
		
	}
	
	@PostMapping("update-img")
	public String updateImg(MultipartFile profile, @SessionAttribute("authentication") Member member) {
		
		memberService.insertProfileImg(member,profile);
		
		return "redirect:/member/edit-profile";
	}
	
	@GetMapping("quit")
	public void quit() {}
	
	@PostMapping("quit")
	@ResponseBody
	public String quitImpl(@RequestBody Member member) {
		
		if(!memberService.findMemberByNickname(member.getNickname()).getPassword().equals(member.getPassword())) {
			return "error";
		}
		return "quit";
	}
	
	@GetMapping("leave")
	public String isLeave(@SessionAttribute("authentication") Member member) {
		memberService.isLeaveMember(member);
		
		return "redirect:/member/login";
	}
	

}
