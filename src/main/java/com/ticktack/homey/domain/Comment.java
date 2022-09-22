package com.ticktack.homey.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMM_ID")
	private Long commId;
	
	@Column(name = "POST_ID")
	private Long postId;
	
	@Column(name = "COMM_UPID")
	private Long commUpid;
	
	@Column(name = "COMM_CONT")
	private String commCont;
	
	@Column(name = "COMM_WRITER")
	private Long commWriter;
	
	@Column(name = "COMM_DATE")
	private Date commDate;
	
	@Column(name = "COMM_UWRITER")
	private Long commUwriter;
	
	@Column(name = "COMM_UDATE")
	private Date commUdate;
	
	@Column(name = "NUM")
	private int num;

	public Long getCommId() {
		return commId;
	}

	public void setCommId(Long commId) {
		this.commId = commId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getCommUpid() {
		return commUpid;
	}

	public void setCommUpid(Long commUpid) {
		this.commUpid = commUpid;
	}

	public String getCommCont() {
		return commCont;
	}

	public void setCommCont(String commCont) {
		this.commCont = commCont;
	}

	public Long getCommWriter() {
		return commWriter;
	}

	public void setCommWriter(Long commWriter) {
		this.commWriter = commWriter;
	}

	public Date getCommDate() {
		return commDate;
	}

	public void setCommDate(Date commDate) {
		this.commDate = commDate;
	}

	public Long getCommUwriter() {
		return commUwriter;
	}

	public void setCommUwriter(Long commUwriter) {
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
		return "Comment [commId=" + commId + ", postId=" + postId + ", commUpid=" + commUpid + ", commCont=" + commCont
				+ ", commWriter=" + commWriter + ", commDate=" + commDate + ", commUwriter=" + commUwriter
				+ ", commUdate=" + commUdate + "]";
	}

/*	@Override
	public String toString() {
		return "댓글 [댓글ID=" + commId + ", 게시글ID=" + postId + ", 댓글상위ID=" + commUpid + ", 댓글내용=" + commCont
				+ ", 작성자=" + commWriter + ", 작성일자=" + commDate + ", 수정자=" + commUwriter
				+ ", 수정일자=" + commUdate + "]";
	}
	
	*/
	
	
	

}
