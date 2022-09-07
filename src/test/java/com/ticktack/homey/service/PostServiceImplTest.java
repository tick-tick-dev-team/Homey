package com.ticktack.homey.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ticktack.homey.domain.Post;
import com.ticktack.homey.repository.post.MemoryPostRepository;


class PostServiceImplTest {
	
	PostService postService;
	MemoryPostRepository postRepository;

	@BeforeEach
	public void beforeEach () {
		postRepository = new MemoryPostRepository();
		postService = new PostServiceImpl(postRepository, null, null);
	}
	
	@AfterEach
	public void afterEach () {
		postRepository.clearStore();
	}
	

	@Test
	@DisplayName("홈 게시물 전체 조회")
	void findAllByHomeId() {
		Post post1 = new Post();
		post1.setPOST_CONT("test1");
		post1.setPOST_HOME(1L);
		postService.createPost(post1);
		
		Post post2 = new Post();
		post2.setPOST_CONT("test2");
		post2.setPOST_HOME(1L);
		postService.createPost(post2);
		
		List<Post> result = postService.findByHomeId(post1.getPOST_HOME());
		assertThat(result.size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("게시물 수정")
	void update() {
		Post post1 = new Post();
		post1.setPOST_CONT("test1");
		post1.setPOST_HOME(1L);
		postService.createPost(post1);
		
		post1.setPOST_CONT("test1 수정");
		Post result = postService.updatePost(post1);
		
		assertThat(result).isEqualTo(post1);
	}
	
	@Test
	@DisplayName("게시물 삭제")
	void delete() {
		Post post1 = new Post();
		post1.setPOST_CONT("test1");
		post1.setPOST_HOME(1L);
		postService.createPost(post1);
		
		postService.deletePost(post1.getPOST_ID());
		
		assertThat(postRepository.existsById(post1.getPOST_ID())).isEqualTo(false);
	}
	

}
