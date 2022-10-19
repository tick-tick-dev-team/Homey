package com.ticktack.homey.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ticktack.homey.auth.PrincipalDetails;
import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.PostForm;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.service.AttachService;
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
	
	@Autowired
	private AttachService attachService;
	
	//첫화면, index페이지, logout시 반환
	@GetMapping("/")
	public String list(Model model) {
		List<Home> homes = homeService.findHomes();
		model.addAttribute("homes", homes);
		return "redirect:/homes";
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
		if(principal != null) {
			User userinfo = userService.findBynick(principal);
			model.addAttribute("writer", userinfo);
		} else {
			model.addAttribute("writer", null);
		}
		
		// 로그인한 사용자
//		User writer = userService.findBynick(principal);
//		model.addAttribute("writer", writer);
		
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
		
		// 프로필 사진 있으면 반환
		if(writer.getAttf_id()!=null) {
			Optional<Attach> profile = attachService.findById(writer.getAttf_id());
			profile.ifPresent(p -> model.addAttribute("attach", p));
		}
		
		return "homes/selectHome";
	}
	
	
	/*myHome페이지(update)*/
	@GetMapping("/homes/{homeId}/update")
	public String updateHomeForm(@PathVariable("homeId") Long homeId, Model model) {
		Home home = homeService.findById(homeId).get();
		model.addAttribute("home", home);
		
		// 프로필 사진 있으면 반환
		if(home.getAttfid()!=null) {
			Optional<Attach> profile = attachService.findById(home.getAttfid());
			profile.ifPresent(p -> model.addAttribute("attach", p));
		}
		
		return "homes/myHome";
	}
	
	/*myHome페이지(update)수정 눌렀을때*/
	@PostMapping("/homes/{homeId}/update")
	public String updateHome(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable("homeId") Long homeid, Home form) {
		Home home = new Home();
		home.setHomeid(form.getHomeid());
		home.setHomename(form.getHomename());
		home.setHomeinst(form.getHomeinst());
		home.setHomethema(form.getHomethema());
		home.setHomeuse(form.getHomeuse());
		home.setUserid(form.getUserid());
		home.setAttfid(form.getAttfid());
		
		homeService.updateHome(home);
		
		
		/*return "redirect:/homes/{homeId}/update";*/
		return "redirect:/homes/{homeId}";
	}
	
	/*userList fetch로 home조회*/
	@PostMapping("/homes/{user_id}/select")
	public Home selectHOME(@PathVariable("user_id") Long user_id) {
		
		Home HomeResult = homeService.findByUserId(user_id).get();
		
		return HomeResult;
	}
	

}
