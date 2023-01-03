
package com.ticktack.homey.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class PostForm {
	// 게시물 ID (auto-increment)
	private Long POST_ID;
	
	// 게시물 내용
	private String POST_CONT;
	
	// 게시물 최초 작성자ID
	private Long POST_WRITER;
	
	// 게시물 최초 등록일
	private Instant POST_DATE;
	
	// 게시물 수정자 ID
	private Long POST_UWRITER;
	
	// 게시물 수정일
	private Instant POST_UPDATE;
	
	// Home ID
	private Long POST_HOME;
	
	// 첨부파일 ID
	private Long ATTF_ID;
	
	// 첨부파일 정보 객체
	private Attach ATTF_OBJ;
	
	// 첨부파일 삭제 여부
	private boolean deleteAttach = false;
	
	// 댓글 리스트
	private List<CommentImgForm> COMMENT_LIST;
	
	// 작성자 닉네임
	private String writerNick;
	
	// 작성자 프로필사진
	private Attach writerProfile;
	
	public String getWriterNick() {
		return writerNick;
	}

	public void setWriterNick(String writerNick) {
		this.writerNick = writerNick;
	}

	public Attach getWriterProfile() {
		return writerProfile;
	}

	public void setWriterProfile(Attach writerProfile) {
		this.writerProfile = writerProfile;
	}

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

//	public String getPOST_DATE() {
//		return POST_DATE;
//	}
//
//	public void setPOST_DATE(String pOST_DATE) {
//		POST_DATE = pOST_DATE;
//	}
	
	
	public Instant getPOST_DATE() {
		return POST_DATE;
	}

	public void setPOST_DATE(Instant pOST_DATE) {
		POST_DATE = pOST_DATE;
	}

	public Long getPOST_UWRITER() {
		return POST_UWRITER;
	}

	public void setPOST_UWRITER(Long pOST_UWRITER) {
		POST_UWRITER = pOST_UWRITER;
	}

	public Instant getPOST_UPDATE() {
		return POST_UPDATE;
	}

	public void setPOST_UPDATE(Instant pOST_UPDATE) {
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
	
	public boolean isDeleteAttach() {
		return deleteAttach;
	}

	public void setDeleteAttach(boolean deleteAttach) {
		this.deleteAttach = deleteAttach;
	}

	public List<CommentImgForm> getCOMMENT_LIST() {
		return COMMENT_LIST;
	}

	public void setCOMMENT_LIST(List<CommentImgForm> cOMMENT_LIST) {
		COMMENT_LIST = cOMMENT_LIST;
	}
	
	
	// PostForm을 Post 객체로 변환해서 반환
	public Post getPostFromPostForm () {
		Post post = new Post();
		
		post.setPOST_ID(this.getPOST_ID());
		post.setPOST_CONT(this.getPOST_CONT());
		post.setPOST_HOME(this.getPOST_HOME());
		
		post.setPOST_DATE(this.getPOST_DATE());
		post.setPOST_UPDATE(this.getPOST_UPDATE());
		
//		Optional.ofNullable(this.getPOST_DATE()).ifPresent(s -> post.setPOST_DATE(LocalDateTime.parse(s)));
//		Optional.ofNullable(this.getPOST_UPDATE()).ifPresent(s -> post.setPOST_UPDATE(LocalDateTime.parse(s)));
		
		post.setPOST_WRITER(this.getPOST_WRITER());
		post.setPOST_UWRITER(this.getPOST_UWRITER());
		
		post.setATTF_ID(this.getATTF_ID());
		
		return post;		
	}


	
	
}
