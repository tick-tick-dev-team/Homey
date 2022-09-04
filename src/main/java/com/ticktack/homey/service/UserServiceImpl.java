package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.User;
import com.ticktack.homey.repository.user.UserRepository;

public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	//회원가입
	@Override
	public User createUser(User user) {
		return userRepository.createUser(user);
	}
	//회원전체조회
	@Override
	public List<User> findByUser(){
		return userRepository.findAll();
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
	public User login(User user) {
		return userRepository.login(user);
	}*/
	//세션확인
	/*public Session(Session session){
		return userRepository.Session(session);
	}
	*/


}
