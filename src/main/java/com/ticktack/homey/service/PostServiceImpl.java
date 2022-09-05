package com.ticktack.homey.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.domain.Post;
import com.ticktack.homey.domain.PostForm;
import com.ticktack.homey.repository.attach.AttachRepository;
import com.ticktack.homey.repository.comment.CommentRepository;
import com.ticktack.homey.repository.post.PostRepository;

public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final AttachRepository attachRepository;

	public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, AttachRepository attachRepository) {
		super();
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
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
		postRepository.delete(postId);
		return postId;
	}

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
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
	

}
