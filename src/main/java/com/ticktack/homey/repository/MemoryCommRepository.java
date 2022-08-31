package com.ticktack.homey.repository;

import java.util.List;

import com.ticktack.homey.domain.Comment;

public class MemoryCommRepository implements CommentRepository {

	/**
	 * 게시글의 댓글 전체 조회
	 * */
	@Override
	public List<Comment> commAllList(Comment comm) {
		return null;
	}

	/**
	 * 게시글의 댓글 총 갯수
	 * */
	@Override
	public int commAllCount(Comment comm) {
		return 0;
	}

	/**
	 * 댓글의 답글 전체 조회
	 * */
	@Override
	public List<Comment> replyAllList(Comment comm) {
		return null;
	}
	
	/**
	 * 댓글의 답글 총 갯수
	 * */
	@Override
	public int replyAllCount(Comment comm) {
		return 0;
	}

	/**
	 * 댓글, 답글 등록
	 * */
	@Override
	public int commInsert(Comment comm) {
		return 0;
	}

	/**
	 * 댓글, 답글 수정
	 * */
	@Override
	public int commUpdate(Comment comm) {
		return 0;
	}

	/**
	 * 댓글, 답글 삭제
	 * */
	@Override
	public int commDelete(Comment comm) {
		return 0;
	}

}
