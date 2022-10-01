package com.ticktack.homey.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ticktack.homey.domain.User;


public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private com.ticktack.homey.repository.user.UserRepository userRepository;
	
	//시큐리티 session{Authentication{UserDetails}}
	@Override
	public UserDetails loadUserByUsername(String usernick) throws UsernameNotFoundException{
		User userEntity = userRepository.findBynick(usernick);
		System.out.println("=============="+ userEntity);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity); //UserDetails 값을 Authentication에 넣어주고 return하면
			//시큐리티 Session에 loadUserByUsername 메소드가 UserDetails 반환값으로 가지고 간다.
			//이렇게 하면 로그인이 완료가 된다.
		}
		return null;
	}
}
