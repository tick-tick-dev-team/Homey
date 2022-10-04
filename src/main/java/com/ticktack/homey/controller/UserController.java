package com.ticktack.homey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.service.HomeService;
import com.ticktack.homey.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private HomeService homeService;
	
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
	public String MyPage2(@PathVariable Long userId, Model model) {
		User result = userService.findById(userId).get();
		model.addAttribute("users", result );
		return "users/myPage";
	}

	

}
