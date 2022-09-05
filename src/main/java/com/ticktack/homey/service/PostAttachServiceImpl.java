package com.ticktack.homey.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Post;
import com.ticktack.homey.domain.PostForm;
import com.ticktack.homey.repository.attach.AttachRepository;
import com.ticktack.homey.repository.post.PostRepository;

public class PostAttachServiceImpl implements PostService{
	// 첨부파일 정보도 가져오는 서비스
	
	private final PostRepository postRepository;
	private final AttachRepository attachRepository;

	public PostAttachServiceImpl(PostRepository postRepository, AttachRepository attachRepository) {
		super();
		this.postRepository = postRepository;
		this.attachRepository = attachRepository;
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
		// 첨부파일 id가 있으면 삭제
		Optional<Long> attf_id = Optional.ofNullable(postRepository.findById(postId).get().getATTF_ID());
		attf_id.ifPresent(id -> {
			attachRepository.delete(id);
		});
		// 게시물 삭제
		postRepository.delete(postId);
		return postId;
	}

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public List<PostForm> findAllByHomeId(Long homeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
