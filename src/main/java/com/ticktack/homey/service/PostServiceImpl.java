package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;
<<<<<<< HEAD
=======
import java.util.stream.Collectors;
>>>>>>> refs/heads/main

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
		// 첨부파일이 있으면 그것도 삭제
//		Optional<Long>attf_id = findAttfIdById(postId);
//		attf_id.ifPresent(id -> attachRepository.delete(id));
		
		postRepository.delete(postId);
		return postId;
	}

<<<<<<< HEAD
=======
	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}
	
	@Override
	public Optional<Long> findAttfIdById(Long postId) {
		return postRepository.findAttfIdById(postId);
	}

	@Override
	public List<PostForm> findAllByHomeId(Long homeId) {
		List<Post> posts = postRepository.findByHomeId(homeId);
		
		List<PostForm> postForms = posts.stream().map(post -> post.getFormFromPost()).collect(Collectors.toList());
		
		postForms.forEach(form -> {
			// 첨부파일 정보 가져오기
			Optional<Attach> attach = attachRepository.findById(form.getATTF_ID());
			attach.ifPresent(f -> {
				form.setATTF_OBJ(f);
			});

			// 댓글
			Comment comment = new Comment();
			comment.setPostId(form.getPOST_ID());
			List<Comment> comments = commentRepository.commAllList(comment);
			
			form.setCOMMENT_LIST(comments);		
		});
		return postForms;
	}
	

>>>>>>> refs/heads/main
}
