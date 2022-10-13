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
	public String myPageUpdate(User form, @RequestParam("updatePw") String updatePw, @RequestParam("updatePwConfirm") String updatePwConfirm, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("************ UserController : myPageUpdate");
		boolean pwd = passwordEncoder.matches(form.getUserpass(), principal.getPassword());

		System.out.println("복호화 비밀번호 : "+ pwd);
		System.out.println(form.toString());
		
		model.addAttribute("users", form );
		
		return "users/myPage";
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

	

}
