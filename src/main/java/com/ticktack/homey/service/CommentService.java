package com.ticktack.homey.service;

import java.util.List;

import com.ticktack.homey.domain.Comment;

public interface CommentService {
	
	// 댓글 조회
	List<Comment> commAllList(Comment comm);
	
	// 답글 조회
	List<Comment> replyAllList(Comment comm);
	
	// 댓글, 답글 등록
	Comment commInsert(Comment comm);
	
	// 댓글, 답글 수정
	Comment commUpdate(Comment comm);
	
	// 댓글, 답글 삭제
	int commDelete(Comment comm);
	
	

}
