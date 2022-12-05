package com.ticktack.homey;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class AuthFail extends SimpleUrlAuthenticationFailureHandler{
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String errorMessage;
		
		if(exception instanceof BadCredentialsException) {
			errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
		} else {
			errorMessage = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
		}
		
		
		
		
		saveException(request, exception);
		
		errorMessage = URLEncoder.encode(errorMessage, "UTF-8"); /* 한글 인코딩 문제 방지 */
		setDefaultFailureUrl("/loginForm?error=true&exception="+errorMessage);
		
		super.onAuthenticationFailure(request, response, exception);
		
	}

}
