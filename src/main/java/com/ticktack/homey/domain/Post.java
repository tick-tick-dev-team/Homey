package com.ticktack.homey.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Post {
	
	// 게시물 ID (auto-increment)
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	Long POST_ID;
	
	// 게시물 내용
	@Column(name="POST_CONT")
	String POST_CONT;
	
	// 게시물 최초 작성자ID
	@Column(name="POST_WRITER")
	Long POST_WRITER;
	
	// 게시물 최초 등록일
	@Column(name="POST_DATE")
	LocalDateTime POST_DATE;
	
	// 게시물 수정자 ID
	@Column(name="POST_UWRITER")
	Long POST_UWRITER;
	
	// 게시물 수정일
	@Column(name="POST_UPDATE")
	LocalDateTime POST_UPDATE;
	
	// Home ID
	@Column(name="HOME_ID")
	Long POST_HOME;
	
	// 첨부파일 ID
	@Column(name="ATTF_ID")
	Long ATTF_ID;

	
	public Long getPOST_ID() {
		return POST_ID;
	}
	public void setPOST_ID(Long pOST_ID) {
		POST_ID = pOST_ID;
	}
	public String getPOST_CONT() {
		return POST_CONT;
	}
	public void setPOST_CONT(String pOST_CONT) {
		POST_CONT = pOST_CONT;
	}
	public Long getPOST_WRITER() {
		return POST_WRITER;
	}
	public void setPOST_WRITER(Long pOST_WRITER) {
		POST_WRITER = pOST_WRITER;
	}
	public LocalDateTime getPOST_DATE() {
		return POST_DATE;
	}
	public void setPOST_DATE(LocalDateTime pOST_DATE) {
		POST_DATE = pOST_DATE;
	}
	public Long getPOST_UWRITER() {
		return POST_UWRITER;
	}
	public void setPOST_UWRITER(Long pOST_UWRITER) {
		POST_UWRITER = pOST_UWRITER;
	}
	public LocalDateTime getPOST_UPDATE() {
		return POST_UPDATE;
	}
	public void setPOST_UPDATE(LocalDateTime pOST_UPDATE) {
		POST_UPDATE = pOST_UPDATE;
	}
	public Long getPOST_HOME() {
		return POST_HOME;
	}
	public void setPOST_HOME(Long pOST_HOME) {
		POST_HOME = pOST_HOME;
	}
	public Long getATTF_ID() {
		return ATTF_ID;
	}
	public void setATTF_ID(Long aTTF_ID) {
		ATTF_ID = aTTF_ID;
	}
	
	// Post를 PostForm 객체로 변환해서 반환

	public PostForm getFormFromPost() {
		
		PostForm form = new PostForm();
		
		form.setPOST_ID(this.getPOST_ID());
		form.setPOST_CONT(this.getPOST_CONT());
		form.setPOST_HOME(this.getPOST_HOME());
		
		form.setPOST_DATE(this.getPOST_DATE());
		form.setPOST_UPDATE(this.getPOST_UPDATE());
		
//		Optional.ofNullable(this.getPOST_DATE()).ifPresent(s -> form.setPOST_DATE(s.toString()));
//		Optional.ofNullable(this.getPOST_UPDATE()).ifPresent(s -> form.setPOST_UPDATE(s.toString()));
		
		form.setPOST_WRITER(this.getPOST_WRITER());
		form.setPOST_UWRITER(this.getPOST_UWRITER());
		
		form.setATTF_ID(this.getATTF_ID());
		
		return form;
	}
	
}
