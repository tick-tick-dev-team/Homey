package com.ticktack.homey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ticktack.homey.repository.attach.AttachRepository;
import com.ticktack.homey.repository.attach.MemoryAttachRepository;
import com.ticktack.homey.repository.comment.CommentRepository;
import com.ticktack.homey.repository.comment.MemoryCommRepository;
import com.ticktack.homey.repository.post.MemoryPostRepository;
import com.ticktack.homey.repository.post.PostRepository;
import com.ticktack.homey.service.attach.AttachService;
import com.ticktack.homey.service.attach.AttachServiceImpl;
import com.ticktack.homey.service.comment.CommentService;
import com.ticktack.homey.service.comment.CommentServiceImpl;
import com.ticktack.homey.service.post.PostService;
import com.ticktack.homey.service.post.PostServiceImpl;

@Configuration
public class SpringConfig {
	
	/* 게시글 */
	@Bean
	public PostRepository postRepository() {
		return new MemoryPostRepository();
	}
	
	@Bean
	public PostService postService() {
		return new PostServiceImpl(postRepository(), commentRepository(), attachRepository());
	}
	
	/* 첨부파일 */
	@Bean
	public AttachRepository attachRepository() {
		return new MemoryAttachRepository();
	}
	
	@Bean
	public AttachService attachService () {
		return new AttachServiceImpl(attachRepository());
	}
	
	/* 댓글, 답글 */
	@Bean
	public CommentRepository commentRepository() {
		return new MemoryCommRepository();
	}
	@Bean
	public CommentService commentService() {
		return new CommentServiceImpl(commentRepository());
	}
}
