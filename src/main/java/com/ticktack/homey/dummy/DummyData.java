package com.ticktack.homey.dummy;

import com.ticktack.homey.service.AttachService;
import com.ticktack.homey.service.CommentService;
import com.ticktack.homey.service.PostService;

public interface DummyData {
	
	// 더미 유저
	public void setUsers ();
	
	
	// 더미 게시물
	public void setPosts ();
	
	
	// 더미 댓글
	public void setComments(Long postId);
	
	
	// 더미 첨부파일 정보
	public Long setAttach(Long postId);

}
