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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.eatsMap.common.code.ErrorCode;
import com.kh.eatsMap.common.exception.HandlableException;
import com.kh.eatsMap.common.validator.ValidatorResult;
import com.kh.eatsMap.firebase.PushMessaging;
import com.kh.eatsMap.member.model.dto.Follow;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.member.validator.EmailForm;
import com.kh.eatsMap.member.validator.EmailFormValidator;
import com.kh.eatsMap.member.validator.JoinForm;
import com.kh.eatsMap.member.validator.JoinFormValidator;
import com.kh.eatsMap.member.validator.ModifyForm;
import com.kh.eatsMap.member.validator.ModifyFormValidator;
import com.kh.eatsMap.myeats.model.dto.Group;
import com.kh.eatsMap.myeats.model.dto.Like;

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
	private PushMessaging push = PushMessaging.getInstance();

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
		
		if(memberAndNotice == null) {
			redirectAttr.addFlashAttribute("message", "아이디나 비밀번호가 틀렸습니다.");
			return "redirect:/member/login";
		}
		Member certifiedUser = (Member) memberAndNotice.get("member");
		Notice notice = (Notice) memberAndNotice.get("notice");
		
		session.setAttribute("authentication", certifiedUser);
		session.setAttribute("notice", notice );
		session.setAttribute("noticeCnt", memberAndNotice.get("noticeCnt"));
		
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
		
		if(kakaoMember.getId() != null) {
			session.setAttribute("authentication", kakaoMember);
			return "kakaoLogin";
		}else {
			return "kakaoJoin";
		}
	}
	
	@GetMapping("kakao-join")
	public void kakaoJoin() {}
	
	@PostMapping("kakao-join")
	public String kakaoJoinImpl(Member member, RedirectAttributes redirectAttr) {		
		memberService.saveMemberBySocial(member);
		
		redirectAttr.addFlashAttribute("message", "환영합니다. 회원님");
		return "redirect:/member/login";
	}
	
	@GetMapping("edit-profile")
	public void editProfile(Model model) {
		model.addAttribute(new ModifyForm()).addAttribute("error", new ValidatorResult().getError());
	}
	
	@PostMapping("edit-profile")
	public String editProfileImpl(@Validated ModifyForm modifyForm, Errors errors
								,@RequestParam(name = "profile", required = false) MultipartFile photo
								,@SessionAttribute("authentication") Member member
								,Model model) {
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error",vr.getError());		//Map<errorField, defaultMessage> 형태 -> jsp에서 error.field명 출력
		
		if(errors.hasErrors()) {
			vr.addErrors(errors);
			return "member/edit-profile";
		}
		memberService.updateMemberProfile(member,modifyForm,photo);
		
		return "redirect:/myeats/post";
	}
	@PostMapping("social-edit-profile")
	public String socialeditProfileImpl(@RequestParam(name = "profile", required = false) MultipartFile photo
								,@RequestParam(name = "nickname") String nickname
								,@SessionAttribute("authentication") Member member
								,Model model) {
		member.setNickname(nickname);
		memberService.updateMemberProfile(member,null,photo);
		
		return "redirect:/myeats/post";
	}	
	
	@GetMapping("quit")
	public void quit() {}
	
	@PostMapping("quit")
	@ResponseBody
	public String quitImpl(@RequestBody Member member) {

		if(!memberService.quitImpl(member)) {
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

		memberService.updateNoticeForDel(id, notice);
		cnt -= 1;
		
		if(cnt == 0) {
			session.removeAttribute("notice");
		}
		session.setAttribute("noticeCnt", cnt);
	}
	
	@GetMapping("saveToken/{currentToken}")	
	@ResponseBody
	public void saveToken(@PathVariable String currentToken, @SessionAttribute("authentication") Member member) {
		//logger.debug("token받아오기 : " + currentToken);
		member.setToken(currentToken);
		memberService.saveMember(member);
	}
	
	//follow-feed
	@GetMapping("follow/{nickname}")
	public String follow(@PathVariable String nickname, @SessionAttribute("authentication") Member member
						,Model model) {
		
		if(member.getNickname().equals(nickname)) {
			return "redirect:/myeats/post";
		}
		Member writer = memberService.findMemberByNickname(nickname);
		Map<String,Object> commandMap = memberService.findMemberAndReviewByMemberId(writer.getId(), member);

		model.addAllAttributes(commandMap);
		return "member/follow";
	}
	
	@PostMapping("follow")
	@ResponseBody
	public String followImpl(@RequestBody Follow followUser, @SessionAttribute("authentication") Member member) {
		memberService.followMember( member.getId(), followUser);
		memberService.updateNotice("follow", memberService.findNoticeByMemberId(followUser.getFollowingId()));
		
		Member to = memberService.findMemberById(followUser.getFollowingId());
		push.push(to);
		
		return to.getId().toString();
	}
	
	@PostMapping("follow-cancel")
	@ResponseBody
	public String followCancel(@RequestBody Follow followUser, @SessionAttribute("authentication") Member member) {
		memberService.followCancel(member.getId(), followUser);
		memberService.updateNoticeForDel("follow", memberService.findNoticeByMemberId(followUser.getFollowingId()));
		
		return memberService.findMemberById(followUser.getFollowingId()).getId().toString();
	}
	
	@PostMapping("follow-pop")
	@ResponseBody
	public List<Map<String,Object>> followPop(@RequestBody Member member) {
		return memberService.findAllFollowingToMap(member);
	}
	
	@PostMapping("follower-pop")
	@ResponseBody
	public Map<String, Object> followerPop(@RequestBody Member member) {
		return memberService.findAllFollowerToMap(member);
	}
	
	//파이어베이스
	@GetMapping("push-test")
	public void pushTest() {}

}
