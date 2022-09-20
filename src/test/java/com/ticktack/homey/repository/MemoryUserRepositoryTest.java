package com.ticktack.homey.repository;

import org.junit.jupiter.api.Test;

import com.ticktack.homey.domain.User;
import com.ticktack.homey.repository.user.MemoryUserRepository;
import com.ticktack.homey.repository.user.UserRepository;

public class MemoryUserRepositoryTest {
	
	UserRepository repository = new MemoryUserRepository();
	
	@Test
	public void createUser() {
		User user = new User();
		user.setUsernick("사장");
		//user.setUserbirth(ToDate("1987-06-05"));
		user.setUserpass("1234");
		
		
		repository.createUser(user);
		
		User result = repository.findById(user.getUser_id()).get();
		System.out.println("result = " + (result == user));
		
	}
}
