package com.kh.eatsMap.common.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import com.kh.eatsMap.common.code.ErrorCode;
import com.kh.eatsMap.common.exception.HandlableException;
import com.kh.eatsMap.member.model.dto.Member;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  throws Exception{
		String[] uriArr = request.getRequestURI().split("/");
		
		if(uriArr.length != 0) {
			switch (uriArr[1]) {
				case "/":
					if(request.getSession().getAttribute("authentication") == null) {
						throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
					}
					break;
				case "member":
					memberAuthorize(request, response, uriArr);
					break;
				case "board":
					boardAuthorize(request, response, uriArr);
					break;
				case "main":
					mainAuthorize(request, response, uriArr);
					break;	
				default:
					break;
			}
		}
		return true;
	}
	

	private void mainAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) {
		
		HttpSession session = httpRequest.getSession();
		if(session.getAttribute("authentication") == null){
			throw new HandlableException(ErrorCode.UNAUTHORIZED_PAGE);
		}
	}


	private void boardAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) {
			HttpSession session = httpRequest.getSession();
			Member member = (Member) session.getAttribute("authentication");
			
			switch (uriArr[2]) {
				case "board-form":
					if(member == null) {
						throw new HandlableException(ErrorCode.UNAUTHORIZED_PAGE);
					}
					break;
				case "upload":
					if(member == null) {
						throw new HandlableException(ErrorCode.UNAUTHORIZED_PAGE);
					}
					break;
			default:
				break;
			}
	}


	private void memberAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) throws ServletException, IOException {

		switch (uriArr[2]) {
		case "mypage":
			if(httpRequest.getSession().getAttribute("authentication") == null) {
				throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
			}
			break;
		default:
			break;
		}
	}
}
