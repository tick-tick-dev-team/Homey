package com.ticktack.homey.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		return "comment/CommList_postForm";
	}
	
	/**
	 * 댓글 등록
	 * */
	@PostMapping("/commentAdd")
	@ResponseBody
	public Comment commentAdd(@RequestBody Comment comm , Model model) {
		Comment result = new Comment();
		
		result = commentService.commInsert(comm);
		System.out.println(result.toString());
		
		return result;
	}
	
	/**
	 * 댓글 삭제
	 * */
	@DeleteMapping("/commentDelete")
	@ResponseBody
	public boolean commentDelete(@RequestBody Comment comm , Model model) {
		boolean removeResult = false;
		
		// 해당 댓글 or 답글 찾기
		Comment result = commentService.findById(comm).get();
		
		if(result != null) {
			removeResult = commentService.commDelete(result);	
			System.out.println("삭제 결과값 : " + removeResult);
		}
		return removeResult;
	}
	
	/**
	 * 댓글 수정
	 * */
	@PostMapping("/commentUpdate")
	@ResponseBody
	public Comment commentUpdate(@RequestBody Comment comm , Model model) {
			
		Comment result = commentService.findById(comm).get();
		result.setCommCont(comm.getCommCont());
		result = commentService.commUpdate(result);
		
		System.out.println("수정 : " + result.toString());
		
		return result;
	}
	

}
