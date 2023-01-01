package com.ticktack.homey.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticktack.homey.auth.PrincipalDetails;
import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.HomeForm;
import com.ticktack.homey.domain.HomeFormFile;
import com.ticktack.homey.domain.PostForm;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.file.FileStore;
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
	
	// 파일 업로드
	@Autowired
	private FileStore fileStore;
	
	//첫화면, index페이지, logout시 반환
	@GetMapping("/")
	public String list(Model model) {
		List<Home> homes = homeService.findByHomes();
		model.addAttribute("homes", homes);
		return "redirect:/homes";
	}
	
	//login페이지
	@GetMapping("/loginForm")
	public String loginForm(@RequestParam(value ="error", required = false) String error, @RequestParam(value="exception", required = false) String exception, Model model){
		/*에러와 예외를 모델에 담는다 https://velog.io/@jyleedev/%ED%9A%8C%EC%9B%90-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%8B%A4%ED%8C%A8*/
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);		

		return "loginForm";
	}
	
	@GetMapping("/loginForm?error=true&exception=")
	public String loginFormE(@RequestParam(value ="error", required = false) String error, @RequestParam(value="exception", required = false) String exception, Model model){
		/*에러와 예외를 모델에 담는다 https://velog.io/@jyleedev/%ED%9A%8C%EC%9B%90-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%8B%A4%ED%8C%A8*/
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);		

		return "loginForm";
	}
	
	
	//로그인 프로세스는 스프링시큐리티에서 /login method = "post"로 제공된다.

	//로그인 성공시, 첫화면, index페이지, logout시 반환, 검색화면
	@GetMapping("/homes")
	public String hometown(@AuthenticationPrincipal PrincipalDetails principal, Model model, @RequestParam(value="keyword", required = false) String keyword) {
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
		
		if(keyword==null) {
			List<Home> homes = homeService.findByHomes();
			model.addAttribute("homes", homes);
			System.err.println("HERE ======="+keyword);
		} else {
			//List<Home> homes = homeService.findByHomes();
			List<Home> homes = homeService.findByKeyword(keyword);
			model.addAttribute("homes", homes);
			System.err.println("HERE???? ======="+keyword);
		}
		
		//db의 로그인한 유저정보 조회, 필요시 @AuthenticationPrincipal과 PrincipalDetails 파라미터와 함께 사용
		if(principal != null) {
			User userinfo = userService.findBynick(principal);
			model.addAttribute("writer", userinfo);

			Home homeInfo = homeService.findByUserId(principal.getUser().getUser_id()).get();
			model.addAttribute("userhome", homeInfo);
		} else {
			model.addAttribute("writer", null);
		}
		
		
		return "homes/Homes";
	}
	

	//selectHome
	@GetMapping("/homes/{homeId}")
	public String selectHome(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable("homeId") Long homeId, Model model) {
		
		// 모든 post 중 homeId가 같은 것만 골라내어 리스트 형태로 반환
		List<PostForm> postFormList = postService.findAllByHomeId(homeId);
		
		// 로그인한 사용자
		User writer = userService.findBynick(principal);
		model.addAttribute("writer", writer);
		
		// 홈 정보(집 이름, 집 설명)
		Home home = homeService.findById(homeId).get();
		model.addAttribute("home", home);
		
		// 홈 주인 정보
		User owner = userService.findById(home.getUserid()).get();
		model.addAttribute("owner", owner);
		
		model.addAttribute("postList", postFormList);
		model.addAttribute("homeId", homeId);
		
		// 홈 사진 있으면 반환
		if(home.getAttfid()!=null) {
			Optional<Attach> profile = attachService.findById(home.getAttfid());
			profile.ifPresent(p -> model.addAttribute("attach", p));
		}
		
		Home homeInfo = homeService.findByUserId(principal.getUser().getUser_id()).get();
		model.addAttribute("userhome", homeInfo);
		
		return "homes/selectHome";
	}
	
	
	/*myHome페이지(update)*/
	@GetMapping("/homes/{homeId}/update")
	public String updateHomeForm(@PathVariable("homeId") Long homeId, Model model) {
		
		HomeForm home = homeService.findByHFId(homeId);
		model.addAttribute("home", home);
		
		return "homes/myHome";
	}
	
	
	/*myHome페이지(update)수정 눌렀을때*/
	@PostMapping("/homes/{homeId}/update")
	public String updateHome(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable("homeId") Long homeid, HomeFormFile form, RedirectAttributes redirectAttributes, Model model) throws IllegalStateException, IOException {
		
		Attach attach = fileStore.storeFile(form.getAttf_obj());

		//새로운 파일 있는 경우
		if(attach!=null) {
			if(form.getAttfid()!=null) {
				// 기존 파일 삭제
				fileStore.deleteStoreFile(Optional.ofNullable(homeService.findByHFId(homeid).getAttf_obj()));
			}
			form.setAttfid(homeService.createAttach(attach).getATTF_ID());
		} else {
			// 새로운 파일 없음 & 기존 파일 삭제하는 경우
			if(form.isDeleteAttach() && form.getAttfid()!=null) { 
				// 기존파일 삭제
				fileStore.deleteStoreFile(attachService.findById(form.getAttfid()));
				form.setAttfid(null);
			}
		}

		form.setHomeid(homeid);

		// DB에 게시물 저장
		Home updatedHome = form.getHomeFromMFHome();
		homeService.updateHome(updatedHome);
		redirectAttributes.addAttribute("homeid", form.getHomeid());

		//selecthome으로 반환
		return "redirect:/homes/{homeId}";
	}
	
	
	
	
	
	/*myHome페이지(update)*/
	@GetMapping("/homes/{homeId}/updateASIS")
	public String updateHomeFormASIS(@PathVariable("homeId") Long homeId, Model model) {
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
	@PostMapping("/homes/{homeId}/updateASIS")
	public String updateHomeASIS(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable("homeId") Long homeid, Home form) {
		//수정
		homeService.updateHome(form);
		//selecthome으로 반환
		return "redirect:/homes/{homeId}";
	}
	
	/*
	 * 회원목록 fetch로 권한 변경
	 * */
	@ResponseBody
	@PostMapping("/homes/{user_id}/select")
	public User selectRole(@PathVariable Long user_id, @RequestBody User user ) {
		User result = userService.findById(user_id).get();
		result.setUserpower(user.getUserpower());
		userService.updateUser(result);
		result = userService.findById(user_id).get();
		if(!result.getUserpower().equals(user.getUserpower())){
			result.setUserpower(null);
		}
		return result;
	}
	
	/* 
	 * 회원목록 fetch로 홈 사용여부 변경
	 * */
	@ResponseBody
	@PostMapping("/homes/{homeId}/homeUse")
	public Home selectHOME(@PathVariable Long homeId, @RequestBody Home home ) {

		Home result = homeService.findById(homeId).get();
		result.setHomeuse(home.getHomeuse());
		homeService.updateHome(result);
		result = homeService.findById(homeId).get();
		
		if(!result.getHomeuse().equals(home.getHomeuse())){
			result.setHomeuse(null);
		}
		return result;
	}
		

}
	