package com.kh.eatsMap.common.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.eatsMap.common.code.ErrorCode;
import com.kh.eatsMap.common.exception.HandlableException;

public class AuthInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  throws Exception{
		String[] uriArr = request.getRequestURI().split("/");
		
		if(uriArr.length != 0) {
			switch (uriArr[1]) {
				case "main":
					if(request.getSession().getAttribute("authentication") == null) {
						throw new HandlableException(ErrorCode.REDIRECT_LOGIN_PAGE);
					}
					break;
				case "member":
					memberAuthorize(request, response, uriArr);
					break;
				case "timeline":
					if(request.getSession().getAttribute("authentication") == null) {
						throw new HandlableException(ErrorCode.REDIRECT_LOGIN_PAGE);
					}
					break;
				default:
					break;
			}
		}
		return true;
	}
	

	private void memberAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) throws ServletException, IOException {
		
		switch (uriArr[2]) {
		case "edit-profile":
			if(httpRequest.getSession().getAttribute("authentication") == null) {
				throw new HandlableException(ErrorCode.REDIRECT_LOGIN_PAGE);
			}
			break;
		case "quit":
			if(httpRequest.getSession().getAttribute("authentication") == null) {
				throw new HandlableException(ErrorCode.REDIRECT_LOGIN_PAGE);
			}
			break;
		default:
			break;
		}
	}
}
