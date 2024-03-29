package com.ticktack.homey.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.domain.CommentImgForm;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.repository.attach.AttachRepository;
import com.ticktack.homey.service.CommentService;
import com.ticktack.homey.service.UserService;

@Controller
public class CommentController {

	private final CommentService commentService;
	private final UserService userService;
	private final AttachRepository attachRepository;
	
	public CommentController(CommentService commentService, UserService userService, AttachRepository attachRepository) {
		this.commentService = commentService;
		this.userService = userService;
		this.attachRepository = attachRepository;
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
	public CommentImgForm commentAdd(@RequestBody Comment comm , Model model) {
		Comment result = new Comment();
		
		result = commentService.commInsert(comm);
		CommentImgForm form = result.getFormFromComment();
		User u = userService.findById(form.getCommWriter()).get();
		form.setUserNick(u.getUsernick());
		if(u.getAttf_id()!=null) {
			form.setATTF_OBJ(attachRepository.findById(u.getAttf_id()).get());
		}
			
		return form;
	}
	
	/**
	 * 댓글 삭제
	 * */
	@PostMapping("/commentDelete")
	@ResponseBody
	public boolean commentDelete(@RequestBody Comment comm , Model model) {
		boolean removeResult = false;

		// 해당 댓글 or 답글 찾기
		// Comment result = commentService.findById(comm).get();
		removeResult = commentService.commDelete(comm);

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
		
		return result;
	}
	

}
