package com.ticktack.homey.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class PostForm {
	// 게시물 ID (auto-increment)
	Long POST_ID;
	
	// 게시물 내용
	String POST_CONT;
	
	// 게시물 최초 작성자ID
	Long POST_WRITER;
	
	// 게시물 최초 등록일
	String POST_DATE;
	
	// 게시물 수정자 ID
	Long POST_UWRITER;
	
	// 게시물 수정일
	String POST_UPDATE;
	
	// Home ID
	Long POST_HOME;
	
	// 첨부파일 ID
	Long ATTF_ID;
	
	// 첨부파일 정보 객체
	Attach ATTF_OBJ;
	
	// 댓글 리스트
	List<Comment> COMMENT_LIST;

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

	public String getPOST_DATE() {
		return POST_DATE;
	}

	public void setPOST_DATE(String pOST_DATE) {
		POST_DATE = pOST_DATE;
	}

	public Long getPOST_UWRITER() {
		return POST_UWRITER;
	}

	public void setPOST_UWRITER(Long pOST_UWRITER) {
		POST_UWRITER = pOST_UWRITER;
	}

	public String getPOST_UPDATE() {
		return POST_UPDATE;
	}

	public void setPOST_UPDATE(String pOST_UPDATE) {
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

	public Attach getATTF_OBJ() {
		return ATTF_OBJ;
	}

	public void setATTF_OBJ(Attach aTTF_OBJ) {
		ATTF_OBJ = aTTF_OBJ;
	}

	public List<Comment> getCOMMENT_LIST() {
		return COMMENT_LIST;
	}

	public void setCOMMENT_LIST(List<Comment> cOMMENT_LIST) {
		COMMENT_LIST = cOMMENT_LIST;
	}
	
	
}
