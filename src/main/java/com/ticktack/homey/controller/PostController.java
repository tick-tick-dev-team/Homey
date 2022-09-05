package com.ticktack.homey.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.domain.Post;
import com.ticktack.homey.domain.PostForm;
import com.ticktack.homey.service.AttachService;
import com.ticktack.homey.service.CommentService;
import com.ticktack.homey.service.PostService;

@Controller
public class PostController {

	private final PostService postService;
	private final AttachService attachService;
	private final CommentService commentService;
	
	public PostController(PostService postService, AttachService attachService, CommentService commentService) {
		super();
		this.postService = postService;
		this.attachService = attachService;
		this.commentService = commentService;
	}
	
	// test용 selectHome
	@GetMapping("/homes/{homeId}")
	public String selectHomeTest (@PathVariable("homeId")Long homeId, Model model) {
		// 더미데이터 삽입
		setPosts(postService, names, contents);
				
		//List<Post> postList = postService.findByHomeId(homeId);
		List<PostForm> postFormList = postService.findAllByHomeId(homeId);
		
		
		model.addAttribute("postList", postFormList);
		model.addAttribute("homeId", homeId);
		
		return "homes/selectHome";
	}
	
	
	
	// 게시물 등록 폼 조회
	@GetMapping("/posts/{homeId}/new")
	public String createPostForm(@PathVariable("homeId")Long homeId, Model model) {
		model.addAttribute("homeId", homeId);
		return "posts/createPostForm";
	}
	
	
	// 게시물 등록
	@PostMapping("/posts/{homeId}/new")
	public String createPost (@PathVariable("homeId")Long homeId, Post post) {
		post.setPOST_HOME(homeId);
		postService.createPost(post);
		return "redirect:/homes/" + homeId;
	}	
	
	// 게시물 수정 폼 조회
	@GetMapping("/posts/{homeId}/update/{postId}")
	public String updatePostForm(@PathVariable("homeId")Long homeId, @PathVariable("postId")Long postId, Model model) {
		Post post = postService.findById(postId).get();
		model.addAttribute("post", post);
		return "posts/updatePostForm";
	}
	
	
	// 게시물 수정
	@PostMapping("/posts/{homeId}/update/{postId}")
	public String updatePost (@PathVariable("homeId")Long homeId, @PathVariable("postId")Long postId,
			PostForm form) {
		Post post = new Post();
		post.setPOST_ID(form.getPOST_ID());
		post.setPOST_HOME(form.getPOST_HOME());
		post.setPOST_UWRITER(form.getPOST_UWRITER());
		post.setPOST_CONT(form.getPOST_CONT());
		post.setPOST_DATE(LocalDateTime.parse(form.getPOST_DATE()));
		
		postService.updatePost(post);
		return "redirect:/homes/" + homeId;
	}
	
	
	// 게시물 삭제
	@PostMapping("/posts/{homeId}/delete/{postId}")
	public String deletePost(@PathVariable("homeId")Long homeId, @PathVariable("postId")Long postId) {
		postService.deletePost(postId);
		return "redirect:/homes/" + homeId;
	}
	
	// 더미데이터 삽입
	private String[] names = {"224", "사장", "팝도"};
	private String[] contents = {"게시물1", "게시물2", "게시물3"};
	
	private void setPosts (PostService postService, String[] names, String[] contents) {
		List<Post> result = postService.findAll();
		
		if(result.size()==0) {
			System.out.println("더미 게시물 삽입 시작");
			for (int i=0; i<names.length; i++) {
				for (String content: contents) {
					
					Post post = new Post();
					Long userId = i+1L;
					post.setPOST_HOME(userId);
					post.setPOST_CONT(names[i] + "의 " + content);
					post.setPOST_WRITER(userId);

					postService.createPost(post);
					setComments(commentService, names, post.getPOST_ID());
				}
			}
		}
	}
	
	private void setComments (CommentService commentService, String[] names, Long postId) {
		System.out.println("더미 댓글 삽입 시작");
		for (int i = 0; i<names.length; i++) {
			Comment comment = new Comment();
			Long userId = i + 1L;
			comment.setPostId(postId);
			comment.setCommCont(names[i] + "님의 댓글");
			comment.setCommWriter(userId.toString());
			
			commentService.commInsert(comment);
		}
		// List<Comment> result = commentService.commAllList(comm);
	}
	
	
	
}
