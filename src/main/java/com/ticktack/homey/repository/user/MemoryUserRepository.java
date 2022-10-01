package com.ticktack.homey.repository.user;


import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.ticktack.homey.domain.User;

@Repository
public class MemoryUserRepository implements UserRepository{

	private static Map<Long, User> store = new HashMap<>(); //<키 자료형, 값 자료형>
	private static long sequence = 0L; //0은 INT0 0L은 LONG0, sequence가 번호 생성
	
	//회원가입
	@Override
	public User createUser(User user) {
		//id는  시스템이 정함
		user.setUser_id(++sequence);
				
		store.put(user.getUser_id(), user);
		return user;
	}


	//중복 닉 거르기 위해서, //로그인 로직을 위한 조회
	@Override
	public Optional<User> findByNick(String usernick) {
		//같은 경우에는 필터링, 찾으면 Optional로 찾은 것을 반환
		return store.values().stream()
				.filter(user->user.getUsernick().equals(usernick))
				.findAny();
	}
	
	//로그인을 위해.. 못하겠음;;
	/*@Override
	public User findBynick(String usernick) {
		return null;
	}*/

	//회원전체조회
	@Override
	public List<User> findUsers(){
		return new ArrayList<>(store.values());
	}
	
	//회원상세조회
	@Override
	public Optional<User> findById(Long userid) {
		//null이여도 반환가능
		return Optional.ofNullable(store.get(userid));
	}
	
	
	
	
	//회원정보수정
	@Override
	public User updateUser(User user){
		user.setUserpass(new String());
		user.setUsernick(new String());
		user.setUserjoin(new Date());
		user.setUserpower(new String());
		user.setUserbirth(new Date());
		
		store.put(user.getUser_id(), user);
		return user;
	}

	//회원탈퇴
	@Override
	public void deleteUser(Long userid) {
		store.remove(userid);
	}
	
	//로그인시 패스워드
	@Override
	public Optional<User> findByPass(String userpass){
		return Optional.ofNullable(store.get(userpass));
	}


	@Override
	public User findBynick(String usernick) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*@Override
	public Session{
	}*/
	
	
	

}
