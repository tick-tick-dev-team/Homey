package com.ticktack.homey.domain;

import java.util.Date;

public class PostWithFile {
		
	// 게시물 객체
	Post POST_OBJ;
	
	// 첨부파일 정보 객체
	Attach ATTF_OBJ;
	

	public PostWithFile(Post pOST_OBJ, Attach aTTF_OBJ) {
		super();
		POST_OBJ = pOST_OBJ;
		ATTF_OBJ = aTTF_OBJ;
	}

	public Post getPOST_OBJ() {
		return POST_OBJ;
	}

	public void setPOST_OBJ(Post pOST_OBJ) {
		POST_OBJ = pOST_OBJ;
	}

	public Attach getATTF_OBJ() {
		return ATTF_OBJ;
	}

	public void setATTF_OBJ(Attach aTTF_OBJ) {
		ATTF_OBJ = aTTF_OBJ;
	}
	
	
}
