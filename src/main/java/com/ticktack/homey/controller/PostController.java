package com.ticktack.homey.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.Post;
import com.ticktack.homey.domain.PostForm;
import com.ticktack.homey.domain.PostFormFile;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.dummy.DummyData;
import com.ticktack.homey.file.FileStore;
import com.ticktack.homey.repository.post.PostRepository;
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

	// 파일 업로드
	private final FileStore fileStore;
	
	public PostController(PostService postService, AttachService attachService, 
			CommentService commentService, FileStore fileStore, DummyData dummyData) {
		super();
		this.postService = postService;
		this.attachService = attachService;
		this.commentService = commentService;
		this.fileStore = fileStore;
		this.dummyData = dummyData;
	}

	// test용 selectHome
	@GetMapping("/homes/{homeId}")
	public String selectHomeTest (@PathVariable("homeId")Long homeId, Model model) {
		// 더미 게시물 삽입
		//dummyData.setPosts();
				
		//List<Post> postList = postService.findByHomeId(homeId);
		List<PostForm> postFormList = postService.findAllByHomeId(homeId);
		
		// 더미 첨부파일 정보, 댓글, 대댓글 삽입
//		for (PostForm form : postFormList) {
//			// form.setATTF_ID(dummyData.setAttach(form.getPOST_ID()));
//			dummyData.setComments(form.getPOST_ID());
//			dummyData.setReplyComments(form.getPOST_ID());
//		}
		// 더미 집주인
		User dummyUser = dummyData.getUser(homeId.intValue());
		
		// 더미 홈
		Home dummyHome = dummyData.getHome(dummyUser);
		
		model.addAttribute("owner", dummyUser);
		model.addAttribute("home", dummyHome);
		
		model.addAttribute("postList", postService.findAllByHomeId(homeId));
		model.addAttribute("homeId", homeId);
		
		return "homes/selectHome";
	}

	// 첨부 이미지 조회
	@ResponseBody
	@GetMapping("/images/{filename}")
	public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
		
		// file:D:/practice/file/diec-e93k.png
		// url이라 띄어쓰기까지 정확해야 한다 
		// 보안 취약
		return new UrlResource("file:" + fileStore.getFullPath(filename));
	}
	
	// @ResponseBody 대신 ResponseEntity : header 정보 추가 가능
	@GetMapping("/attach/{postId}")
	public ResponseEntity<Resource> downloadAttach(@PathVariable Long postId) throws MalformedURLException {

		Attach attach = postService.findById(postId).getATTF_OBJ();

		String storeFileName = attach.getATTF_SERNM();
		String originalFileName = attach.getATTF_REALNM();
		
		UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
		System.out.println("uploadFileName=" + originalFileName);
		// log.info("uploadFileName={}", originalFileName);
		
		// 한글 깨짐 방지 UTF-8 인코딩
		String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
		
		// header 정보 추가 안하면 url은 파일을 보여주기만 함
		String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(urlResource);
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
	public String createPost (@PathVariable("homeId")Long homeId, PostFormFile form,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		
		// multipart file에서 attach 추출
		Optional<Attach> attach = Optional.ofNullable(fileStore.storeFile(form.getATTF_OBJ()));
		
		// attach, post DB에 저장
		form.setPOST_HOME(homeId);
		attach.ifPresent(a -> {
			form.setATTF_ID(postService.createAttach(a).getATTF_ID());
		});
		postService.createPost(form.getPostFromPostForm());
		
		redirectAttributes.addAttribute("homeId", form.getPOST_HOME());
		
		return "redirect:/homes/{homeId}";
	}	
	
	// 게시물 수정 폼 조회
	@GetMapping("/posts/{homeId}/update/{postId}")
	public String updatePostForm(@PathVariable("homeId")Long homeId, @PathVariable("postId")Long postId, Model model) {
		
		PostForm post = postService.findById(postId);
		
		// 더미 로그인 유저
		model.addAttribute("writer", dummyData.getUser(homeId.intValue()));
		
		model.addAttribute("post", post);
		
		return "posts/updatePostForm";
	}
	
	
	// 게시물 수정
	@PostMapping("/posts/{homeId}/update/{postId}")
	public String updatePost (@PathVariable("homeId")Long homeId, @PathVariable("postId")Long postId,
			PostFormFile form, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		
		// multipart file에서 attach 추출
		Attach attach = fileStore.storeFile(form.getATTF_OBJ());
		if(attach!=null) {
			// 기존 파일 삭제
			fileStore.deleteStoreFile(Optional.ofNullable(postService.findById(postId).getATTF_OBJ()));
			form.setATTF_ID(postService.createAttach(attach).getATTF_ID());
		}
		// DB에 저장
		form.setPOST_HOME(homeId);

		Post updatedPost = form.getPostFromPostForm();
		postService.updatePost(updatedPost);
		
		redirectAttributes.addAttribute("homeId", form.getPOST_HOME());
		return "redirect:/homes/{homeId}";
	}
	
	
	// 게시물 삭제
	@PostMapping("/posts/{homeId}/delete/{postId}")
	public String deletePost(@PathVariable("homeId")Long homeId, @PathVariable("postId")Long postId) throws IOException {
		
		fileStore.deleteStoreFile(Optional.ofNullable(postService.findById(postId).getATTF_OBJ()));
		postService.deletePost(postId);
		
		return "redirect:/homes/" + homeId;
	}
	
}