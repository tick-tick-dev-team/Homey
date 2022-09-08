package com.ticktack.homey.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.dummy.DummyData;
import com.ticktack.homey.service.CommentService;

@Controller
public class CommentController {

	private final CommentService commentService;
	private final DummyData dummyData;
	
	public CommentController(CommentService commentService, DummyData dummyData) {
		this.commentService = commentService;
		this.dummyData = dummyData;
	}
	
	
	/**
	 * 게시글 테스트
	 * */
	@GetMapping("/postList")
	public String postListMain() {
		return "comment/PostList";
	}
	
	/**
	 * 댓글 목록
	 * */
	@GetMapping("/comment")
	public String CommListMain(String num, Model model) {
		Long postId = Long.parseLong(num);
		
		// 더미데이터
		dummyData.setComments(postId);
		
		Comment comm = new Comment();
		comm.setPostId(postId);
		List<Comment> result = commentService.commAllList(comm);
		
		model.addAttribute("commList", result);
		model.addAttribute("postId", postId);
		return "comment/CommList";
	}
	
	/**
	 * ajax 
	 * */
	@GetMapping("/commentAdd")
	@ResponseBody
	public String commentAdd(Model model) {
		System.out.println("ajax 호출!");
		return "success!!";
	}
	

}
