package com.ticktack.homey.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ticktack.homey.domain.User;
import com.ticktack.homey.repository.user.UserRepository;

public class PrincipalDetails implements UserDetails {

	private User user;
	private UserRepository userRepository;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	@SuppressWarnings("serial")
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getUserpower();
			}
		}); 
		return collect;
	}
	

	@Override
	public String getPassword() {
		return user.getUserpass();
	}

	@Override
	public String getUsername() {
		return user.getUsernick();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//사용하지 않을 것은 true로 변경해준다.
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		//우리 사이트 ~기간동안 로그인 안하면 휴면 계정으로 돌리겠다. 
		return true;
	}
}
