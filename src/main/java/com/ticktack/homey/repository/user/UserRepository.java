package com.ticktack.homey.repository.user;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.User;


public interface UserRepository {
		//회원가입
		User createUser(User user);
		
		//중복체크 시 사용할 NICK NULL인 경우 고려
		Optional<User> findByNick(String usernick);
		
		//로그인조회
		User findBynick(String usernick);
		
		//회원전체조회
		List<User> findUsers();
		
		//회원상세조회
		Optional<User> findById(Long userid);
		
		//회원정보수정
		User updateUser(User user);
		
		//회원탈퇴
		void deleteUser(Long userid);
		
		//로그인시 패스워드
		Optional<User> findByPass(String userpass);
		


	
}
