package com.ticktack.homey.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
	
/*	주 객체인 User 엔티티에 @OneToOne 선언 이후 대상 테이블인 Home 객체를 선언
	User객체를 통해 Home의 정보 조회를 가능하게 함
	https://blog.advenoh.pe.kr/database/JPA-%EC%9D%BC%EB%8C%80%EC%9D%BC-One-To-One-%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84/
	*/
	
	/*@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="user_id")
	private Home home;
*/
	
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
	/*public Home getHome() {
		return home;
	}
	public void setHome(Home home) {
		this.home = home;
	}*/
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", userpass=" + userpass + ", usernick=" + usernick + ", userjoin="
				+ userjoin + ", userpower=" + userpower + ", userbirth=" + userbirth + ", attf_id=" + attf_id + "]";
	}
	
	
	

}
