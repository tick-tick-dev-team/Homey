package com.ticktack.homey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
      return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/posts/**").authenticated()
			.antMatchers("/homes/{\\d+}").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/loginForm")
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/homes")
			.usernameParameter("usernick") //아이디 파라미터명
			.passwordParameter("userpass")
			.and()
			.logout()
	        .logoutSuccessUrl("/homes") // 로그아웃 성공시 리다이렉트 주소
	        .invalidateHttpSession(true) // 로그아웃 이후 세션 전체 삭제 여부
			.deleteCookies("JSESSIONID")
			;
		return http.build();
	}
}
