package com.ticktack.homey.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.domain.Post;
import com.ticktack.homey.domain.PostForm;
import com.ticktack.homey.dummy.DummyData;
import com.ticktack.homey.service.AttachService;
import com.ticktack.homey.service.CommentService;
import com.ticktack.homey.service.PostService;

@Controller
public class PostController {

	private final PostService postService;
	private final AttachService attachService;
	private final CommentService commentService;
	// 더미데이터 가져오기
	private final DummyData dummyData;
	
	public PostController(PostService postService, AttachService attachService, 
			CommentService commentService, DummyData dummyData) {
		super();
		this.postService = postService;
		this.attachService = attachService;
		this.commentService = commentService;
		this.dummyData = dummyData;
	}

	// test용 selectHome
	@GetMapping("/homes/{homeId}")
	public String selectHomeTest (@PathVariable("homeId")Long homeId, Model model) {
		// 더미 게시물 삽입
		dummyData.setPosts();
				
		//List<Post> postList = postService.findByHomeId(homeId);
		List<PostForm> postFormList = postService.findAllByHomeId(homeId);
		
		// 더미 첨부파일 정보, 댓글, 대댓글 삽입
		for (PostForm form : postFormList) {
			dummyData.setAttach(form.getPOST_ID());
//			dummyData.setComments(form.getPOST_ID());
//			dummyData.setReplyComments(form.getPOST_ID());
		}
		
		// 더미 집주인
		model.addAttribute("owner", dummyData.getUser(homeId.intValue()));
		
		model.addAttribute("postList", postFormList);
		model.addAttribute("homeId", homeId);
		
		return "homes/selectHome";
	}
	
	
	
	// 게시물 등록 폼 조회
	@GetMapping("/posts/{homeId}/new")
	public String createPostForm(@PathVariable("homeId")Long homeId, Model model) {
		model.addAttribute("homeId", homeId);
		// 더미 로그인 유저
		model.addAttribute("writer", dummyData.getUser(homeId.intValue()));
		
		return "posts/createPostForm";
	}
	
	
	// 게시물 등록
	@PostMapping("/posts/{homeId}/new")
	public String createPost (@PathVariable("homeId")Long homeId, PostForm post) {
		post.setPOST_HOME(homeId);
		
		Post newPost = post.getPostFromPostForm();
		postService.createPost(newPost);
		
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
			PostForm post) {
		
		Post updatedPost = post.getPostFromPostForm();
		postService.updatePost(updatedPost);
		
		return "redirect:/homes/" + homeId;
	}
	
	
	// 게시물 삭제
	@PostMapping("/posts/{homeId}/delete/{postId}")
	public String deletePost(@PathVariable("homeId")Long homeId, @PathVariable("postId")Long postId) {
		// 첨부파일 id 있으면 첨부파일 정보 삭제
		Optional<Long>attf_id = postService.findAttfIdById(postId);
		attf_id.ifPresent(id -> attachService.deleteAttach(id));
		
		postService.deletePost(postId);
		
		return "redirect:/homes/" + homeId;
	}
	
}