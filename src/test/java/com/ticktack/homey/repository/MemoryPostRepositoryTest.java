package com.ticktack.homey.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ticktack.homey.domain.Post;
import com.ticktack.homey.repository.post.MemoryPostRepository;

class MemoryPostRepositoryTest {
	
	MemoryPostRepository repository = new MemoryPostRepository();
	
	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}

	@Test
	@DisplayName("게시물 생성")
	void save() {
		// given
		Post post = new Post();
		post.setPOST_CONT("test1");
		
		// when
		repository.save(post);
		
		// then
		Post result = repository.findById(post.getPOST_ID()).get();
		assertThat(result).isEqualTo(post);
	}
	
	@Test
	@DisplayName("게시물 전체조회")
	void findAll () {
		// given
		Post post = new Post();
		post.setPOST_CONT("test1");
		repository.save(post);
		
		Post post2 = new Post();
		post2.setPOST_CONT("test2");
		repository.save(post2);
		
		Post post3 = new Post();
		post3.setPOST_CONT("test3");
		repository.save(post3);
		
		// when
		List<Post> result = repository.findAll();
		
		// then
		assertThat(result.size()).isEqualTo(3);
	}
	
	@Test
	@DisplayName("홈 게시물 전체조회")
	void findByHomeId () {
		// given
		Post post = new Post();
		post.setPOST_HOME(1L);
		post.setPOST_CONT("test1");
		repository.save(post);
		
		Post post2 = new Post();
		post2.setPOST_CONT("test2");
		post2.setPOST_HOME(1L);
		repository.save(post2);
		
		Post post3 = new Post();
		post3.setPOST_CONT("test3");
		post3.setPOST_HOME(2L);
		repository.save(post3);
		
		// when
		List<Post> result = repository.findByHomeId(post.getPOST_HOME());
		
		// then
		assertThat(result.size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("게시물 하나 조회")
	void findById () {
		// given
		Post post = new Post();
		post.setPOST_CONT("test1");
		post.setPOST_HOME(1L);
		repository.save(post);
		
		Post post2 = new Post();
		post2.setPOST_CONT("test2");
		post.setPOST_HOME(1L);
		repository.save(post2);
		
		// when
		Post result = repository.findById(post2.getPOST_ID()).get();
		
		// then
		assertThat(result).isEqualTo(post2);
	}
	
	@Test
	@DisplayName("게시물 수정")
	void update () {
		// given
		Post post = new Post();
		post.setPOST_CONT("test1");
		post.setPOST_HOME(1L);
		repository.save(post);
		
		// when
		post.setPOST_CONT("test1 - 수정");
		repository.update(post);
		Post result = repository.findById(post.getPOST_ID()).get();
		
		// then
		assertThat(result).isEqualTo(post);
	}
	
	
	@Test
	@DisplayName("게시물 삭제")
	void delete () {
		// given
		Post post = new Post();
		post.setPOST_CONT("test1");
		post.setPOST_HOME(1L);
		repository.save(post);
		
		Post post2 = new Post();
		post2.setPOST_CONT("test2");
		post.setPOST_HOME(1L);
		repository.save(post2);
		
		// when
		repository.delete(post.getPOST_ID());
		
		// then
		assertThat(repository.existsById(post.getPOST_ID())).isEqualTo(false);
	}
	
	
	

}
