package com.kh.eatsMap.member.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.eatsMap.common.code.ErrorCode;
import com.kh.eatsMap.common.exception.HandlableException;
import com.kh.eatsMap.common.validator.ValidatorResult;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.member.model.repository.NoticeRepository;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.EmailFormValidator;
import com.kh.eatsMap.member.validator.JoinForm;
import com.kh.eatsMap.member.validator.JoinFormValidator;
import com.kh.eatsMap.member.validator.ModifyForm;
import com.kh.eatsMap.member.validator.ModifyFormValidator;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.service.TimelineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final MemberService memberService;
	private final JoinFormValidator joinFormValidator;
	private final EmailFormValidator emailFormValidator;
	private final ModifyFormValidator modifyFormValidator;
	

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
		
		Map<String, Object> memberAndNotice = memberService.authenticateUser(member);
		Notice notice = (Notice) memberAndNotice.get("notice");
		Member certifiedUser = (Member) memberAndNotice.get("member");
		
		if(certifiedUser == null) {
			redirectAttr.addFlashAttribute("message", "아이디나 비밀번호가 틀렸습니다.");
			return "redirect:/member/login";
		}
		
		session.setAttribute("authentication", certifiedUser);
		session.setAttribute("notice", notice );
		session.setAttribute("noticeCnt", memberAndNotice.get("noticeCnt"));
		
		logger.debug(notice.toString());
		logger.debug(memberAndNotice.get("noticeCnt").toString());
		
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
	public void logout(HttpSession session) {
		session.removeAttribute("authentication");
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
		redirectAttr.addFlashAttribute("joinMsg","회원가입을 위한 이메일이 발송되었습니다.");
		
		return "redirect:/member/login/";
	}
	
	@GetMapping("nickname-check")
	@ResponseBody
	public String nicknameCheck(String nickname) {
		Member member = memberService.findMemberByNickname(nickname);
		
		return (!nickname.equals("false") && member == null) ? "available" : "disabled";
	}
	
	@PostMapping("kakao-login")
	@ResponseBody
	public String kakaoLoginImpl(@RequestBody Member member, HttpSession session) {
		Member kakaoMember = memberService.findKakaoMember(member.getKakaoId());
						
		if(kakaoMember != null) {
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
	
	@GetMapping("removeNotice")
	@ResponseBody
	public void removeNotice(String id, @SessionAttribute("authentication") Member member
							, @SessionAttribute("notice") Notice notice, @SessionAttribute("noticeCnt") int cnt, HttpSession session) {

		memberService.updateNotice(id, notice);
		cnt -= 1;
		
		if(cnt == 0) {
			session.removeAttribute("notice");
			session.removeAttribute("noticeCnt");
		}
		session.setAttribute("noticeCnt", cnt);
	}
	
	@GetMapping("saveToken/{clientToken}")
	public void saveToken(@PathVariable String clientToken, @SessionAttribute("authentication") Member member) {
		logger.debug("token받아오기 : " + clientToken);
		member.setToken(clientToken);
		memberService.saveMember(member);
	}
	
	//follow-feed
	@GetMapping("follow/{memberId}")
	public String follow(@PathVariable ObjectId memberId, @SessionAttribute("authentication") Member member
						,Model model) {
		
		if(member.getId() == memberId) {
			return "redirect:/myeats/post";
		}
		Follow follow = memberService.findFollowByMemberId(memberId, member.getId());
		Map<String,Object> commandMap = memberService.findMemberAndReviewByMemberId(memberId);

		model.addAllAttributes(commandMap)
			.addAttribute("follow",follow); 
		
		return "member/follow";
	}
	
	//follow
	@PostMapping("follow")
	@ResponseBody
	public void followImpl(@RequestBody Follow followUser, @SessionAttribute("authentication") Member member) {
		logger.debug("팔로우 할 id : " + followUser.toString());
		memberService.followMember( member.getId(), followUser);
	}
	
	@PostMapping("follow-cancel")
	@ResponseBody
	public void followCancel(@RequestBody Follow followUser, @SessionAttribute("authentication") Member member) {
		memberService.followCancel(member.getId(), followUser);
	}
	
	//파이어베이스
	@GetMapping("push-test")
	public void pushTest() {}

}
