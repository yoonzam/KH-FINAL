package com.kh.eatsMap.timeline.controller;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eatsMap.common.util.PageObject;
import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.member.model.dto.Notice;
import com.kh.eatsMap.member.model.service.MemberService;
import com.kh.eatsMap.timeline.model.dto.Review;
import com.kh.eatsMap.timeline.model.service.TimelineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("timeline")
public class TimelineController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final TimelineService timelineService;
	private final MemberService memberService;
    
	@GetMapping("/")
	public String timeline(Model model, @SessionAttribute("authentication") Member member ,HttpSession session) {	//유진 12/06
		Notice notice = memberService.findNoticeByMemberId(member.getId());
		int noticeCnt = notice.getCalendarNotice() + notice.getGroupNotice() + notice.getParticipantNotice() + notice.getFollowNotice();
		session.setAttribute("notice", notice);
		session.setAttribute("noticeCnt", noticeCnt);
		
		PageObject pageObject = new PageObject(1, 8);
		List<Review> reviews = timelineService.findAllReviews(pageObject, member);
		model.addAttribute("reviews", reviews);
		return "timeline/timeline";
	}
	
	@PostMapping("/")
	@ResponseBody
	public List<HashMap<String, Object>> timelinePaging(int page, @SessionAttribute("authentication") Member member) {
		PageObject pageObject = new PageObject(page, 8);
		return timelineService.findReviewsForPaging(pageObject, member);
	}
	
	@PostMapping("upload")
	@ResponseBody
	public void upload(Review review, double latitude, double longitude, List<MultipartFile> photos, @SessionAttribute("authentication") Member member) {
		timelineService.insertReview(review, latitude, longitude, photos, member);
	}
	
	@GetMapping("detail")
	@ResponseBody
	public Map<String, Object> detail(String id, @SessionAttribute("authentication") Member member) {
		return timelineService.findReviewById(id, member);
	}
	
	@GetMapping("edit")
	@ResponseBody
	public Map<String, Object> edit(String id, @SessionAttribute("authentication") Member member) {
		return timelineService.findReviewById(id, member);
	}
	
	@PostMapping("edit")
	@ResponseBody
	public void editRevice(
			Review review, String reviewId, List<MultipartFile> photos, String latitude, String longitude, @RequestParam(value="hdPhotos") List<String> hdPhotos,
			@SessionAttribute("authentication") Member member) {
		timelineService.editReview(review, reviewId, member, latitude, longitude, photos, hdPhotos);
	}
	
	@PostMapping("delete")
	@ResponseBody
	public void deleteRevice(String id, @SessionAttribute("authentication") Member member) {
		timelineService.deleteReview(id, member);
	}
	
	@PostMapping("like")
	@ResponseBody
	public void likeReview(String revId, @SessionAttribute("authentication") Member member) {
		timelineService.saveLike(revId, member);
	}
	
	@PostMapping("unlike")
	@ResponseBody
	public void unlikeReview(String revId, @SessionAttribute("authentication") Member member) {
		timelineService.deleteLike(revId, member);
	}
	
	@PostMapping("group")
	@ResponseBody
	public List<Map<String, String>> group(@SessionAttribute("authentication") Member member) {
		return timelineService.findGroup(member);
	}
	
	@GetMapping("search")
	public void search(String keyword_, String[] area_, String[] category_, String[] hashtag_, Model model, @SessionAttribute("authentication") Member member) {
String keyword = keyword_ == null ? "" : keyword_;
		
		String[] area = new String[0];
		String[] paramArea = new String[0];
		if(area_ != null) {
			area = new String[area_.length];	
			paramArea = new String[area_.length];
			for (int i = 0; i < area_.length; i++) {
				area[i] = area_[i];
				paramArea[i] = "&area_=" + area_[i]; 
			}	
		}
		String paramAreaString = Arrays.stream(paramArea).collect(Collectors.joining());
		
		String[] category = new String[0];
		String[] paramCate = new String[0];
		if(category_ != null) {
			category = new String[category_.length];	
			paramCate = new String[category_.length];
			for (int i = 0; i < category_.length; i++) {
				category[i] = category_[i];
				paramCate[i] = "&category_=" + category_[i];
			}
		}
		String paramCateString = Arrays.stream(paramCate).collect(Collectors.joining());
		
		String[] hashtag = new String[0];
		String[] paramHash = new String[0];
		if(hashtag_ != null) {
			hashtag = new String[hashtag_.length];	
			paramHash = new String[hashtag_.length];
			for (int i = 0; i < hashtag_.length; i++) {
				hashtag[i] = hashtag_[i];
				paramHash[i] = "&hashtag_=" + hashtag_[i];
			}	
		}
		String paramHashString = Arrays.stream(paramHash).collect(Collectors.joining());
		
		PageObject pageObject = new PageObject(1, 8);
		List<Review> searchedReviewList = timelineService.searchReview(pageObject, keyword, area, category, hashtag, member);
		model.addAttribute("reviews", searchedReviewList);
		model.addAttribute("keyword", keyword_);
		model.addAttribute("area", paramAreaString);
		model.addAttribute("category", paramCateString);
		model.addAttribute("hashtag", paramHashString);
		model.addAttribute("pageObject", pageObject);
	}
	
	@PostMapping("search")
	@ResponseBody
	public List<HashMap<String, Object>> searchForPaging(@RequestParam(value = "keyword") String keyword, String[] area, String[] category, String[] hashtag, int page, @SessionAttribute("authentication") Member member) {
		PageObject pageObject = new PageObject(page, 8);
		return timelineService.searchReviewForPaging(pageObject, keyword, area, category, hashtag, member);
	}
	
}
