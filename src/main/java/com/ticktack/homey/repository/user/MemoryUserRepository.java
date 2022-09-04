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
	private static long sequence = 0L; //0은 INT0 0L은 LONG0
	
	//회원가입
	@Override
	public User createUser(User user) {
		user.setUserid(++sequence);
		user.setUserpass(new String());
		user.setUsernick(new String());
		user.setUserjoin(new Date());
		user.setUserpower(new String());
		user.setUserbirth(new Date());
		
		
		store.put(user.getUserid(), user);
		return user;
	}


	//중복 닉 거르기 위해서
	@Override
	public Optional<User> findByNick(String usernick) {
		return store.values().stream()
				.filter(user->user.getUsernick().equals(usernick))
				.findAny();
	}

	//회원전체조회
	@Override
	public List<User> findByUser(){
		return new ArrayList<>(store.values());
	}
	
	//회원상세조회
	@Override
	public Optional<User> findById(Long userid) {
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
		
		store.put(user.getUserid(), user);
		return user;
	}

	//회원탈퇴
	@Override
	public void deleteUser(Long userId) {
		store.remove(userId);
	}
	
	@Override
	public User login(User user){
		user.getUserid();
		user.getUserpass();
		/*https://kitty-geno.tistory.com/131 를 봤는데 헷갈려짐*/	
		return user;
	}
	
	/*@Override
	public Session{
	}*/
	
	
	//모든 회원 조회
	@Override
	public List<User> findAll() {
		return new ArrayList<>(store.values());
	}
	

}
