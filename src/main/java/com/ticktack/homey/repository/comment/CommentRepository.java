package com.ticktack.homey.repository.comment;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Comment;

public interface CommentRepository {
	
	// 댓글 조회
	List<Comment> commAllList(Comment comm);
	
	// 댓글, 답글 등록
	Comment commInsert(Comment comm);
	
	// 댓글, 답글 수정
	Comment commUpdate(Comment comm);
	
	// 댓글 삭제시 답글 모두 삭제
	boolean commDelete(Comment comm);

	// 댓글, 답글 존재 여부
	boolean commExist(Comment comm);
	
	// 게시글 삭제시 해당되는 댓글, 답글 모두 삭제
	void commPostDelete(Long postId);
	
	// 댓글, 답글 한건 조회
	Optional<Comment> findById(Comment comm);
	
	List<Comment> AllList(Long postId);
	
	List<Comment> replyAllList(Long postId);

	// commUpid 입력 메서드
	Comment commUpidUpdate(Comment result);
	

}
