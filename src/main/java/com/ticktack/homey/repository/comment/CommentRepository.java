package com.ticktack.homey.repository.comment;

import java.util.List;

import com.ticktack.homey.domain.Comment;

public interface CommentRepository {
	
	// 댓글 조회
	List<Comment> commAllList(Comment comm);
	
	// 댓글 총 갯수
	int commAllCount(Comment comm);
	
	// 답글 조회
	List<Comment> replyAllList(Comment comm);
	
	// 답글 총 갯수
	int replyAllCount(Comment comm);
	
	// 댓글 등록
	Comment commInsert(Comment comm);
	
	// 답글 등록
	Comment replyInsert(Comment comm);
	
	// 댓글, 답글 수정
	Comment commUpdate(Comment comm);
	
	// 댓글, 답글 삭제
	int commDelete(Comment comm);
	

}
