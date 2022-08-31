package com.ticktack.homey.domain;

import java.util.Date;

public class Comment {
	
	private Long commId;
	
	private Long postId;
	
	private Long commUpid;
	
	private String commCont;
	
	private String commWriter;
	
	private Date commDate;
	
	private String commUwriter;
	
	private Date commUdate;
	

	public Long getCommId() {
		return commId;
	}

	public void setCommId(Long commId) {
		this.commId = commId;
	}

	public Long getPostId() {
		return postId;
	}

	public Long getCommUpid() {
		return commUpid;
	}

	public void setCommUpid(Long commUpid) {
		this.commUpid = commUpid;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getCommCont() {
		return commCont;
	}

	public void setCommCont(String commCont) {
		this.commCont = commCont;
	}

	public String getCommWriter() {
		return commWriter;
	}

	public void setCommWriter(String commWriter) {
		this.commWriter = commWriter;
	}

	public Date getCommDate() {
		return commDate;
	}

	public void setCommDate(Date commDate) {
		this.commDate = commDate;
	}

	public String getCommUwriter() {
		return commUwriter;
	}

	public void setCommUwriter(String commUwriter) {
		this.commUwriter = commUwriter;
	}

	public Date getCommUdate() {
		return commUdate;
	}

	public void setCommUdate(Date commUdate) {
		this.commUdate = commUdate;
	}

	@Override
	public String toString() {
		return "댓글 [댓글ID=" + commId + ", 게시글ID=" + postId + ", 댓글상위ID=" + commUpid + ", 댓글내용=" + commCont
				+ ", 작성자=" + commWriter + ", 작성일자=" + commDate + ", 수정자=" + commUwriter
				+ ", 수정일자=" + commUdate + "]";
	}
	
	

}
