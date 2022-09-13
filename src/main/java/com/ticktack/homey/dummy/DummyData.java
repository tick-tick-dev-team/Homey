package com.ticktack.homey.dummy;

import com.ticktack.homey.domain.User;

public interface DummyData {
	
	// 더미 유저
	public void setUsers ();
	
	
	// 더미 로그인 사용자 가져오기
	public User getUser(int num);
	
	
	// 더미 게시물
	public void setPosts ();
	
	
	// 더미 댓글
	public void setComments(Long postId);
	
	
	// 더미 대댓글
	public void setReplyComments(Long postId);
	
	
	// 더미 첨부파일 정보
	public Long setAttach(Long postId);

}
