package com.ticktack.homey.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ticktack.homey.auth.PrincipalDetails;
import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.service.AttachService;
import com.ticktack.homey.service.HomeService;
import com.ticktack.homey.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private HomeService homeService;
	@Autowired
	private AttachService attachService;
	
    @Autowired    
    private PasswordEncoder passwordEncoder;

	
	/*회원가입폼조회, /users/new*/
	@GetMapping("/users/new")
	public String addUserview(){
		return "users/addUser";
	}
	
	/*회원가입*/
	@PostMapping("users/new")
	public String addUser(User form) {
		System.out.println(form);
		User user = new User();
		user.setUsernick(form.getUsernick());
		user.setUserpass(form.getUserpass());
		user.setUserbirth(form.getUserbirth());
		user.setUserpower("ROLE_USER");
		
		userService.createUser(user);
		
		
		//home테이블에 usernick도 추가 필요없음, 바로 설정한다.
		Home home = new Home();
		home.setUserid(user.getUser_id());
		home.setHomename(form.getUsernick()+"의 집");
		homeService.createHome(home);
		
		return "redirect:/";
	}
	
	/*별명중복체크*/
	@ResponseBody
	@PostMapping("/checkNick") 
	public String checkNick(String usernick){
		System.out.println(usernick);
		String result = userService.checkNick(usernick);
		System.out.println(result);
		return result;
	}
	
	
	/* 사용자 목록 조회 */
	@GetMapping("users")
	public String list(Model model) {
		List<User> users = userService.findUsers();
		model.addAttribute("users", users );
		return "users/userList";
	}
	
	@GetMapping("/users/{userId}")
	public String MyPage2(@PathVariable Long userId, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
		User result = userService.findById(userId).get();
		model.addAttribute("users", result );
		
		System.out.println(principal.getPassword());
		//PasswordEncoder.encode();
		
		
		// 프로필 사진 있으면 반환
		if(result.getAttf_id()!=null) {
			Optional<Attach> profile = attachService.findById(result.getAttf_id());
			profile.ifPresent(p -> model.addAttribute("attach", p));
		}
		return "users/myPage";
	}
	
	/*
	 * 마이페이지 정보 수정
	 * */
	@PostMapping("users/myPage")
	public String myPageUpdate(User form, @RequestParam String nickChage, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("************ UserController : myPageUpdate");
		System.out.println("************ "+ nickChage);
		User result = userService.findById(form.getUser_id()).get();
		if(nickChage != null) {
			result.setUsernick(nickChage);
		}
		result.setUserbirth(form.getUserbirth());
		// user 수정
		userService.updateUser(result);
		
		// 세션 변경 필요
		

		// home name 수정
		Home homeResult = homeService.findByUserId(form.getUser_id()).get();
		homeResult.setHomename(result.getUsernick()+"의 집");
		homeService.updateHome(homeResult);
		System.out.println(homeResult.toString());
		
		return "redirect:/users/"+ form.getUser_id();
	}
	
	/*
	 * 마이페이지 비밀번호 체크
	 * */
	@ResponseBody
	@PostMapping("users/pwdCheck/{userpass}")
	public boolean myPagePwdCheck(@PathVariable String userpass, @AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("************ UserController : myPagePwdCheck");
		return passwordEncoder.matches(userpass, principal.getPassword());
	}
	
	/*
	 * 마이페이지 비밀번호변경 페이지 이동
	 * */
	@GetMapping("/users/{userId}/pwChange")
	public String myPagePasswordChage(@PathVariable Long userId, Model model) {
		System.out.println("************ UserController : myPagePasswordChage");
		User result = userService.findById(userId).get();
		model.addAttribute("users", result );
		return "users/myPagePwUpdate";
	}
	
	@PostMapping("/users/pwUpdate")
	public String pwUpdate(User form, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("************ UserController : pwUpdate");
		System.out.println(form.getUserpass());
		User result = userService.findById(form.getUser_id()).get();
		result.setUserpass(passwordEncoder.encode(form.getUserpass()));
		userService.updateUser(result);
		
		// 세션 변경 필요
		
		
		model.addAttribute("users", result );
		return "users/myPagePwUpdate";
	}

}
