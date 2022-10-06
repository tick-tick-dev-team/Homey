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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ticktack.homey.auth.PrincipalDetails;
import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.PostForm;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.service.HomeService;
import com.ticktack.homey.service.PostService;
import com.ticktack.homey.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	//첫화면, index페이지, logout시 반환
	@GetMapping("/")
	public String list(Model model) {
		List<Home> homes = homeService.findHomes();
		model.addAttribute("homes", homes);
		return "redirect:homes/Homes";
	}
	
	//login페이지
	@GetMapping("/loginForm")
	public String loginForm(){
		return "loginForm";
	}
	
	//로그인 프로세스는 스프링시큐리티에서 /login method = "post"로 제공된다.

	//로그인 성공시, 첫화면, index페이지, logout시 반환
	@GetMapping("/homes")
	public String hometown(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
		List<Home> homes = homeService.findHomes();
		model.addAttribute("homes", homes);
		//model.addAttribute("info", principal.getUsername()+"님");
		
		//db의 로그인한 유저정보 조회, 필요시 @AuthenticationPrincipal과 PrincipalDetails 파라미터와 함께 사용하세요!
		/*User userinfo = userService.findBynick(principal);
		model.addAttribute("userinfo", userinfo);*/
		
		return "homes/Homes";
	}
	

	//selectHome
	@GetMapping("/homes/{homeId}")
	public String selectHome(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable("homeId") Long homeId, Model model) {
		
		System.out.println("HomeController에 들어왔습니다.");
		
		// 모든 post 중 homeId가 같은 것만 골라내어 리스트 형태로 반환
		List<PostForm> postFormList = postService.findAllByHomeId(homeId);
		
		// 로그인한 사용자
		User writer = userService.findBynick(principal);
		
		// 홈 정보(집 이름, 집 설명)
		Home home = homeService.findById(homeId).get();
		
		model.addAttribute("writer", writer);
		
		model.addAttribute("home", home);

		
		model.addAttribute("postList", postFormList);
		model.addAttribute("homeId", homeId);
		
		return "homes/selectHome";
	}

}
