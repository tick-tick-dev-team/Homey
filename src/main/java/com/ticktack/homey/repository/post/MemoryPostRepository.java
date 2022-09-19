package com.ticktack.homey.repository.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ticktack.homey.domain.Post;

public class MemoryPostRepository implements PostRepository {
	
	private static Map<Long, Post> store = new HashMap<>();
	private Long sequence = 0L;

	@Override
	public Post save(Post post) {
		// id, 생성일, 수정일 넣고 저장
		post.setPOST_ID(++sequence);
		post.setPOST_DATE(LocalDateTime.now());
		post.setPOST_UPDATE(post.getPOST_DATE());		
		
		store.put(post.getPOST_ID(), post);
		return store.get(post.getPOST_ID());
	}
	
	@Override
	public List<Post> findByHomeId(Long homeId) {
		// 모든 post 중 homeId가 같은 것만 골라내어 리스트 형태로 반환
		return store.values().stream()
				.filter(post -> post.getPOST_HOME().equals(homeId))
				.collect(Collectors.toList());
	}

	@Override
	public Post update(Post post) {
		// 수정 일시 변경 후 저장
		post.setPOST_UPDATE(LocalDateTime.now());
		store.put(post.getPOST_ID(), post);
		return store.get(post.getPOST_ID());
	}

	@Override
	public void delete(Long postId) {
		// id로 게시물 삭제
		store.remove(postId);
	}

	@Override
	public boolean existsById(Long postId) {
		// 특정 게시물 존재 여부
		return store.containsKey(postId);
	}

	@Override
	public List<Post> findAll() {
		// 사이트의 모든 게시물 조회
		return new ArrayList(store.values());
	}

	@Override
	public Optional<Post> findById(Long postId) {
		// 게시물id로 게시물 하나 조회
		return Optional.ofNullable(store.get(postId));
	}
	
	public void clearStore() {
		store.clear();
	}

	@Override
	public Optional<Long> findAttfIdById(Long postId) {
		// 게시물 첨부파일 id 반환
		return Optional.ofNullable(store.get(postId).getATTF_ID());
	}



}