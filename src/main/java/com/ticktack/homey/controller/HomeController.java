package com.ticktack.homey.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ticktack.homey.auth.PrincipalDetails;
import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.service.HomeService;

@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	//첫화면, index페이지, logout시 반환
	@GetMapping("/")
	public String list(Model model) {
		List<Home> homes = homeService.findHomes();
		model.addAttribute("homes", homes);
		return "index";
	}
	
	//login페이지
	@GetMapping("/loginForm")
	public String loginForm(){
		return "loginForm";
	}
	
	//로그인 프로세스는 스프링시큐리티에서 /login method = "post"로 제공된다.

	//로그인 성공시
	@GetMapping("/homes")
	public String hometown(@AuthenticationPrincipal PrincipalDetails principal, Model model, Model who) {
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
		List<Home> homes = homeService.findHomes();
		model.addAttribute("homes", homes);
		
		who.addAttribute("info", principal.getUsername()+"님");
		
		return "homes/Homes";
	}
	
	//로그인 실패시
	@GetMapping("/failLogin")
	public String fail() {
		return "failLogin";
	}
	
	/*
	 * //로그아웃
	 * 
	 * @GetMapping("/logout") public String logout(HttpServletRequest request,
	 * HttpServletResponse response) { new
	 * SecurityContextLogoutHandler().logout(request, response,
	 * SecurityContextHolder.getContext().getAuthentication()); return "redirect:/";
	 * }
	 */

}
