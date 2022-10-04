package com.ticktack.homey.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user")
public class User {

/* USER_ID              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자ID',
   USER_PASS            VARCHAR(18) NULL COMMENT '비밀번호',
   USER_NICK            VARCHAR(18) NULL COMMENT '닉네임',
   USER_JOIN            DATETIME NOT NULL COMMENT '가입일자',
   USER_POWER			VARCHAR(18) NULL COMMENT '관리권한',
   USER_BIRTH           DATE NULL COMMENT '생년월일',
   ATTF_ID              BIGINT NULL COMMENT '첨부파일ID'*/

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	Long user_id;

	@Column(name="user_pass")
	private String userpass;
	
	@Column(name="user_nick")
	private String usernick;
	
	@Column(name="user_join") @CreationTimestamp
	private Date userjoin;
	
	@Column(name="user_power")
	private String userpower;
	
	@Column(name="user_birth") @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date userbirth;
	
	@Column(name="attf_id")
	private Long attf_id;

	
	public Long getUser_id() {
		return user_id;
	}
/*
 * id를 자동 증가로 만든 경우 setter가 필요하지 않음??
	https://codeutility.org/spring-typemismatchexception-the-provided-id-is-of-a-wrong-type-stack-overflow/
*/	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	public String getUsernick() {
		return usernick;
	}

	public void setUsernick(String usernick) {
		this.usernick = usernick;
	}

	public Date getUserjoin() {
		return userjoin;
	}

	public void setUserjoin(Date userjoin) {
		this.userjoin = userjoin;
	}

	public String getUserpower() {
		return userpower;
	}

	public void setUserpower(String userpower) {
		this.userpower = userpower;
	}

	public Date getUserbirth() {
		return userbirth;
	}

	public void setUserbirth(Date userbirth) {
		this.userbirth = userbirth;
	}

	public Long getAttf_id() {
		return attf_id;
	}

	public void setAttf_id(Long attf_id) {
		this.attf_id = attf_id;
	}
	

	

}
