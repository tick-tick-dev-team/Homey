package com.ticktack.homey.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticktack.homey.auth.PrincipalDetails;
import com.ticktack.homey.auth.PrincipalDetailsService;
import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.file.FileStore;
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
    
    @Autowired
    private PrincipalDetailsService principalDetailsService;
    
    @Autowired
    private FileStore fileStore;

	
	/*회원가입폼조회*/
	@GetMapping("/users/new")
	public String addUserview(){
		return "users/addUser";
	}
	
	/*회원가입*/
	@PostMapping("users/new")
	public String addUser(User form) {

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
		
		return "loginForm";
	}
	
	/*별명중복체크*/
	@ResponseBody
	@PostMapping("/checkNick") 
	public String checkNick(String usernick){
		String result = userService.checkNick(usernick);
		return result;
	}
	
	
	/* 사용자 목록 조회 */
	@GetMapping("users")
	public String list(Model model, @AuthenticationPrincipal PrincipalDetails principal) {
		//사용자 가져오기
		List<User> users = userService.findUsers();
		model.addAttribute("users", users );
		
		//집정보 가져오기
		List<Home> homes = homeService.findHomes();
		
		// 로그인한 사용자
		User writer = userService.findBynick(principal);
		model.addAttribute("writer", writer);
		
		model.addAttribute("homes", homes);	
		
		return "users/userList";
	}
	
	/*마이페이지 조회*/
	@GetMapping("/users/{userId}")
	public String MyPage(@PathVariable Long userId, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
		User result = userService.findById(userId).get();
		model.addAttribute("users", result );
		
		// 프로필 사진 있으면 반환
		if(result.getAttf_id()!=null) {
			Optional<Attach> profile = attachService.findById(result.getAttf_id());
			profile.ifPresent(p -> model.addAttribute("attach", p));
		}
		
		// 로그인한 사용자
		User writer = userService.findBynick(principal);
		model.addAttribute("writer", writer);
		
		Home homeInfo = homeService.findByUserId(principal.getUser().getUser_id()).get();
		model.addAttribute("userhome", homeInfo);
		
		return "users/myPage";
	}
	
	/*마이페이지 정보 수정 */
	@PostMapping("users/myPage")
	public String myPageUpdate(User form, @RequestParam String nickChage, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
		User result = userService.findById(form.getUser_id()).get();
		if(nickChage != form.getUsernick() ) {
			result.setUsernick(nickChage);
		}
		result.setUserbirth(form.getUserbirth());
		// user 수정
		userService.updateUser(result);
		
		// home name 수정
		Home homeResult = homeService.findByUserId(form.getUser_id()).get();
		homeResult.setHomename(result.getUsernick()+"의 집");
		homeService.updateHome(homeResult);
		
		// 세션변경
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, result.getUsernick()));
		
		return "redirect:/users/"+ form.getUser_id();
	}


	/*마이페이지 비밀번호 체크 */
	@ResponseBody
	@PostMapping("users/pwdCheck")
	public boolean myPagePwdCheck(@RequestParam String userpass, @AuthenticationPrincipal PrincipalDetails principal) {
		return passwordEncoder.matches(userpass, principal.getPassword());
	}
	
	/*마이페이지 비밀번호변경 페이지 이동 */
	@GetMapping("/users/{userId}/pwChange")
	public String myPagePasswordChage(@PathVariable Long userId, Model model) {
		User result = userService.findById(userId).get();
		model.addAttribute("users", result );
		return "users/myPagePwUpdate";
	}
	
	/*마이페이지 비밀번호 변경 */
	@PostMapping("/users/pwUpdate")
	public String pwUpdate(User form, Model model, @AuthenticationPrincipal PrincipalDetails principal) {

		User result = userService.findById(form.getUser_id()).get();
		result.setUserpass(passwordEncoder.encode(form.getUserpass()));
		userService.updateUser(result);
		
		// 세션변경
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, result.getUsernick()));
		
		model.addAttribute("users", result );
		return "redirect:/users/"+ form.getUser_id();
	}

	
	/*새로운 세션 생성*/
	protected Authentication createNewAuthentication(Authentication currentAuth, String userNick) {
	    UserDetails newPrincipal = principalDetailsService.loadUserByUsername(userNick);
	    UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(newPrincipal, currentAuth.getCredentials(), newPrincipal.getAuthorities());
	    newAuth.setDetails(currentAuth.getDetails());
	    return newAuth;
	}
	
	
	
	/*생일 찾아오기 */
	@ResponseBody
	@PostMapping("/users/{userId}/birth")
	public String findBirth(@PathVariable Long userId) {

		User result = userService.findById(userId).get();
		String findBirth = result.getUserbirth().toString();
		
		String Birth = findBirth.substring(5, 10);
		
		return Birth;
	}
	
	
	
	/*임시파일 업로드 - 10.08 popdo 추가 
         프로필 등록*/
	@ResponseBody
	@PostMapping("/users/{userId}/profile")
	public Attach createTmp2 (@PathVariable Long userId, MultipartFile file, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		
		// 임시 파일 저장
		if(Optional.ofNullable(file).isPresent()) {
			
			profileCheck(userId); 
			Attach attach = fileStore.storeFile(file);
			
			// attach db에 저장
			attachService.createAttach(attach);
			// user의 프로필로 저장
			Optional<User> user = userService.findById(userId);
			user.ifPresent(u -> {
				u.setAttf_id(attach.getATTF_ID());
				userService.updateUser(u);
			});
			return attach;
		}
		return null;
	}
	
	/* 프로필 파일 리셋 및 리셋 결과값 - 10.08 popdo 추가  */ 
	@ResponseBody
	@PostMapping("/users/{userId}/profileReset/{attfId}")
	public boolean profileReset (@PathVariable Long userId, @PathVariable Long attfId, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {		

		profileCheck(userId);
		
		// 파일 존재여부 확인
		Optional<Attach> deleteAttach = attachService.findById(attfId);
		if(deleteAttach.isPresent()) {
			return false;
		} else {
			return true;
		}

	}
	
	/*
	 * 기존 파일 있는지 확인 삭제 처리 - 10.08 popdo 추가
	 * */
	public void profileCheck(Long userId) {

		Long deleteAttfId;

		Optional<User> user = userService.findById(userId);
		if(user.get().getAttf_id()!= null) {
			deleteAttfId = user.get().getAttf_id();
			user.get().setAttf_id(null);
			Optional<User> test = Optional.ofNullable(userService.updateUser(user.get()));
			if(test.get().getAttf_id() == null) {
				Optional<Attach> deleteAttach = attachService.findById(deleteAttfId);
				if(deleteAttach.isPresent()) {
					attachService.deleteAttach(deleteAttach.get().getATTF_ID());
					try {
						fileStore.deleteStoreFile(deleteAttach);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("--> [Error] deleteAttach.isPresent() : 기존파일이 존재하지 않습니다.");
				} 
			} else {
				System.out.println("--> [Error] test.get().getAttf_id() == null : user의 기존 파일 null 처리가 정상적으로 처리되지 않았습니다.");
			}		
		}
	}
}
