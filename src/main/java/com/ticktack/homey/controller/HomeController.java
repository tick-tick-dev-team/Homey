package com.ticktack.homey.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ticktack.homey.domain.Home;
import com.ticktack.homey.service.HomeService;

@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	//첫화면, index페이지, login페이지, logout시 반환
	@GetMapping("/")
	public String list(Model model) {
		List<Home> homes = homeService.findHomes();
		model.addAttribute("homes", homes);
		return "index";
	}
	
	//로그인 프로세스
	@PostMapping("/doLogin")
	public String loginProc(){
		return "/homes";
	}
	
	//로그인 성공시
	@GetMapping("/homes")
	public String hometown() {
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
