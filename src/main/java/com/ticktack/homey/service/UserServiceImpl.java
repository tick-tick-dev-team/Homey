package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.ticktack.homey.auth.PrincipalDetails;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.repository.user.UserRepository;

public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	//private PasswordEncoder passwordEncoder;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	//회원가입
	@Override
	public User createUser(User user) {
		validateDuplicateNick(user); //중복닉네임검증
		
		//패스워드암호화
		String encodedPassword = passwordEncoder.encode(user.getUserpass());
        user.setUserpass(encodedPassword);
		
		userRepository.createUser(user);
		
		return user;
	}
	
	//중복닉네임검증 원래 코드
	private void validateDuplicateNick(User user) {
		userRepository.findByNick(user.getUsernick())
			.ifPresent(u -> {
				throw new IllegalStateException("이미 존재하는 별명입니다.");
			});
	}
	
	//중복닉네임검증
	@Override
	public String checkNick(String usernick) {
		
		System.out.println(usernick);
		
		Boolean check1 = userRepository.findByNick(usernick).isPresent();
		
		 System.out.println("hi===="+check1);
		 
		 if(check1 == true) {
				return "사용 불가능한 별명입니다.";
			}
		 else {
				return "사용 가능한 별명입니다.";
		 }	
		 
		// return "사용가능합니다."
		 
	}
	
/*	private String check_nick(String usernick) {
		 Optional<User> check = userRepository.findByNick(usernick);
		 return check.orElse(null).toString();
	}*/
	
	//회원전체조회
	@Override
	public List<User> findUsers(){
		return userRepository.findUsers();
	}
	//회원상세조회 
	@Override
	public Optional<User> findById(Long userId){
		return userRepository.findById(userId);
	}
	//회원정보수정
	@Override
	public User updateUser(User user) {
		return userRepository.updateUser(user);
	}
	//회원탈퇴
	@Override
	public Long deleteUser(Long userId) {
		userRepository.deleteUser(userId);
		return userId;
	}

	//usernick으로 회원정보조회
	@Override
	public User findBynick(@AuthenticationPrincipal PrincipalDetails principal) {
		return userRepository.findBynick(principal.getUsername());
	}



}
