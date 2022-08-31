package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Post;

public interface PostService {
	
	// 게시물 전체조회
	public List<Post> findByHomeId(Long homeId);
	
	
	// 게시물 하나 조회
	public Optional<Post> findById(Long postId);
	
	
	// 게시물 생성
	public Post createPost(Post post);
	
	
	// 게시물 수정
	public Post updatePost(Post post);
	
	
	// 게시물 삭제
	public Long deletePost(Long postId);
}
