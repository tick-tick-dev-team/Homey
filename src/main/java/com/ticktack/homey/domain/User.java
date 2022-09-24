package com.ticktack.homey.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User {

/* USER_ID              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자ID',
   USER_PASS            VARCHAR(18) NULL COMMENT '비밀번호',
   USER_NICK            VARCHAR(18) NULL COMMENT '닉네임',
   USER_JOIN            DATETIME NOT NULL COMMENT '가입일자',
   USER_POWER			VARCHAR(18) NULL COMMENT '관리권한',
   USER_BIRTH           DATE NULL COMMENT '생년월일',
   ATTF_ID              BIGINT NULL COMMENT '첨부파일ID'*/

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long user_id;
	
	@Column(name = "USER_PASS")
	private String userpass;
	
	@Column(name = "USER_NICK")
	private String usernick;
	
	@Column(name = "USER_JOIN")
	private Date userjoin;
	
	@Column(name = "USER_POWER")
	private String userpower;
	
	@Column(name = "USER_BIRTH")
	private Date userbirth;
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getUserpower() {
		return userpower;
	}
	public void setUserpower(String userpower) {
		this.userpower = userpower;
	}
	public Long getAttf_id() {
		return attf_id;
	}
	public void setAttf_id(Long attf_id) {
		this.attf_id = attf_id;
	}
	private Long attf_id;
	
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
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getUserbirth() {
		return userbirth;
	}
	public void setUserbirth(Date userbirth) {
		this.userbirth = userbirth;
	}
	
	
	

}
