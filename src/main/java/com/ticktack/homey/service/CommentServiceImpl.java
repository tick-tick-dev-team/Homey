package com.ticktack.homey.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.repository.comment.CommentRepository;

public class CommentServiceImpl implements CommentService {
	
	private final CommentRepository commentRepository;
	
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	/**
	 * 게시글의 댓글 조회
	 * */
	@Override
	public List<Comment> commAllList(Comment comm) {	
		return commentRepository.commAllList(comm);
	}
	
	/**
	 * 게시글의 답글 조회
	 * */
	@Override
	public List<Comment> replyAllList(Comment comm) {
		return commentRepository.replyAllList(comm);
	}
		
	/**
	 * 게시글의 댓글 등록
	 * */
	@Override
	public int commInsert(Comment comm) {
		return commentRepository.commInsert(comm);
	}

	/**
	 * 게시글의 댓글, 답글 수정
	 * */
	@Override
	public int commUpdate(Comment comm) {
		return commentRepository.commUpdate(comm);
	}

	/**
	 * 게시글의 댓글, 답글 삭제
	 * */
	@Override
	public int commDelete(Comment comm) {
		return commentRepository.commDelete(comm);
	}

}
