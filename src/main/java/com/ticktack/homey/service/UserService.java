package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ticktack.homey.domain.User;


@Service
public interface UserService {

	//회원가입
	public String createUser(User user);
	//회원전체조회
	public List<User> findUsers();
	//회원상세조회
	public Optional<User> findById(Long userId);
	//회원정보수정
	public User updateUser(User user);
	//회원탈퇴
	public Long deleteUser(Long userId);
	//로그인
/*	public Boolean login(User user);*/
	//세션확인
	//public Session(Session session);
	
}
