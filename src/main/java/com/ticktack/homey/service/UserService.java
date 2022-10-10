package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.ticktack.homey.auth.PrincipalDetails;
import com.ticktack.homey.domain.User;


@Service
public interface UserService {

	//회원가입
	public User createUser(User user);
	//중복체크
	public String checkNick(String usernick);
	//회원전체조회
	public List<User> findUsers();
	//회원상세조회
	public Optional<User> findById(Long userId);
	//회원정보수정
	public User updateUser(User user);
	//회원탈퇴
	public Long deleteUser(Long userId);
	//usernick으로 회원정보조회
	public User findBynick(@AuthenticationPrincipal PrincipalDetails principal);
	
}
