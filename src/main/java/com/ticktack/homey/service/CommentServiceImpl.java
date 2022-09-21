package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.repository.comment.CommentRepository;

@Transactional
public class CommentServiceImpl implements CommentService {
	
	private final CommentRepository commentRepository;
	
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	/**
	 * 게시글의 댓글,답글 조회
	 * */
	@Override
	public List<Comment> commAllList(Comment comm) {	
		return commentRepository.commAllList(comm);
	}
	
		
	/**
	 * 게시글의 댓글, 답글 등록
	 * */
	@Override
	public Comment commInsert(Comment comm) {
		return commentRepository.commInsert(comm);
	}

	/**
	 * 게시글의 댓글, 답글 수정
	 * */
	@Override
	public Comment commUpdate(Comment comm) {
		return commentRepository.commUpdate(comm);
	}

	/**
	 * 게시글의 댓글, 답글 삭제
	 * */
	@Override
	public boolean commDelete(Comment comm) {
		return commentRepository.commDelete(comm);
	}
	
	/**
	 * 게시글의 댓글, 답글 한건 조회
	 * */
	@Override
	public Optional<Comment> findById(Comment comm) {
		return commentRepository.findById(comm);
	}

	@Override
	public List<Comment> AllList(Long postId) {
		return commentRepository.AllList(postId);
	}

	@Override
	public List<Comment> replyAllList(Long postId) {
		return commentRepository.replyAllList(postId);
	}

}
