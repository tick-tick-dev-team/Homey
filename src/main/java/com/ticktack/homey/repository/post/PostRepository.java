package com.ticktack.homey.repository.post;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Post;

public interface PostRepository {
	
	// 게시물 생성
	Post save(Post post);
	
	// 홈 하나의 게시물 전체조회
	List<Post> findByHomeId(Long homeId);
	
	// 게시물 업데이트
	Post update(Post post);
	
	// 게시물 삭제
	void delete(Long postId);
	
	// 게시물 존재 여부
	boolean existsById(Long postId);
	
	// 테스트용
	// 사이트 게시물 전체 조회
	List<Post> findAll();
	
	// 게시물 1개 조회
	Optional<Post> findById(Long postId);
	
	// 첨부파일 id 조회
	Optional<Long> findAttfIdById (Long postId);
	
}
