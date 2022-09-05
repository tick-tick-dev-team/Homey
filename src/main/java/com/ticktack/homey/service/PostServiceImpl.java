package com.ticktack.homey.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Post;
import com.ticktack.homey.repository.post.PostRepository;

public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}

	@Override
	public List<Post> findByHomeId(Long homeId) {
		return postRepository.findByHomeId(homeId);
	}

	@Override
	public Optional<Post> findById(Long postId) {
		return postRepository.findById(postId);
	}

	@Override
	public Post createPost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Post updatePost(Post post) {
		return postRepository.update(post);
	}

	@Override
	public Long deletePost(Long postId) {
		postRepository.delete(postId);
		return postId;
	}

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}
	

}
