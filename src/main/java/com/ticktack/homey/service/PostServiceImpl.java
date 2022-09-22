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
	public PostForm findById(Long postId) {
		PostForm form = postRepository.findById(postId).get().getFormFromPost();
		
		// 첨부파일 정보 가져오기	
		Optional<Long> attfId = Optional.ofNullable(form.getATTF_ID());
		attfId.ifPresent(id -> {
			form.setATTF_OBJ(attachRepository.findById(id).get());
		});

		// 댓글
		Comment comment = new Comment();
		comment.setPostId(form.getPOST_ID());
		form.setCOMMENT_LIST(commentRepository.commAllList(comment));		
		
		return form;
	}

	@Override
	public Post createPost(Post post) {
		return postRepository.save(post);
	}
	
	@Override
	public Attach createAttach(Attach attach) {
		return attachRepository.save(attach);
	}

	@Override
	public Post updatePost(Post post) {
		return postRepository.update(post);
	}

	@Override
	public Long deletePost(Long postId) {
		// 첨부파일이 있으면 그것도 삭제
		Optional<Long>attf_id = findAttfIdById(postId);
		attf_id.ifPresent(id -> attachRepository.delete(id));
		
		postRepository.delete(postId);
		return postId;
	}
	
	// 첨부파일 정보 삭제
	@Override
	public Long deleteAttach(Long attf_id) {
		attachRepository.delete(attf_id);
		return attf_id;
	}

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
			Optional<Long> attfId = Optional.ofNullable(form.getATTF_ID());
			attfId.ifPresent(id -> {
				form.setATTF_OBJ(attachRepository.findById(id).get());
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