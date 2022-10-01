package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	
	//중복닉네임검증
	private void validateDuplicateNick(User user) {
		userRepository.findByNick(user.getUsernick())
			.ifPresent(u -> {
				throw new IllegalStateException("이미 존재하는 별명입니다.");
			});
	}
	
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
	//로그인
	/*@Override
	public Boolean login(User user) {
		return userRepository.login(user);
	}*/
	//세션확인
	/*public Session(Session session){
		return userRepository.Session(session);
	}
	*/


}
