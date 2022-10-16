package com.ticktack.homey.domain;

import java.util.Date;

public class CommentImgForm {
	
	Long commId;
	
	Long postId;
	
	Long commUpid;
	
	String commCont;
	
	Long commWriter;
	
	Date commDate;
	
	Long commUwriter;
	
	Date commUdate;
	
	Attach ATTF_OBJ;
	
	String userNick;

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

	public Attach getATTF_OBJ() {
		return ATTF_OBJ;
	}

	public void setATTF_OBJ(Attach aTTF_OBJ) {
		ATTF_OBJ = aTTF_OBJ;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	@Override
	public String toString() {
		return "CommentImgForm [commId=" + commId + ", postId=" + postId + ", commUpid=" + commUpid + ", commCont="
				+ commCont + ", commWriter=" + commWriter + ", commDate=" + commDate + ", commUwriter=" + commUwriter
				+ ", commUdate=" + commUdate + ", ATTF_OBJ=" + ATTF_OBJ + ", userNick=" + userNick + "]";
	}
	
	

}
