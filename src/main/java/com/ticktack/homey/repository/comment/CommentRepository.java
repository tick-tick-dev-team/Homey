package com.ticktack.homey.repository.comment;

import java.util.List;

import com.ticktack.homey.domain.Comment;

public interface CommentRepository {
	
	// 댓글 조회
	List<Comment> commAllList(Comment comm);
	
	// 댓글, 답글 등록
	Comment commInsert(Comment comm);
	
	// 댓글, 답글 수정
	Comment commUpdate(Comment comm);
	
	// 댓글, 답글 삭제
	boolean commDelete(Comment comm);

	// 댓글, 답글 존재 여부
	boolean commExist(Comment comm);
	

}
