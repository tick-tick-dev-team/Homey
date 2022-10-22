package com.ticktack.homey.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticktack.homey.auth.PrincipalDetails;
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
import com.ticktack.homey.service.HomeService;
import com.ticktack.homey.service.PostService;
import com.ticktack.homey.service.UserService;

@Controller
public class PostController {

	private final UserService userService;
	private final HomeService homeService;
	private final PostService postService;
	private final AttachService attachService;
	private final CommentService commentService;
	// 더미데이터 가져오기
	private final DummyData dummyData;

	// 파일 업로드
	private final FileStore fileStore;
	
	public PostController(UserService userService, PostService postService, HomeService homeService,
			AttachService attachService, CommentService commentService, 
			FileStore fileStore, DummyData dummyData) {
		super();
		this.userService = userService;
		this.homeService = homeService;
		this.postService = postService;
		this.attachService = attachService;
		this.commentService = commentService;
		this.fileStore = fileStore;
		this.dummyData = dummyData;
	}

	// test용 selectHome
	@GetMapping("/homes/{homeId}/test")
	public String selectHomeTest (@AuthenticationPrincipal PrincipalDetails principal, @PathVariable("homeId")Long homeId, Model model) {
				
		List<PostForm> postFormList = postService.findAllByHomeId(homeId);
		
		// 집주인
		User owner = userService.findById(homeId).get();
		
		// 로그인한 사용자
		User writer = userService.findBynick(principal);
		
		// 홈 정보
		Home home = homeService.findById(homeId).get();
		
		model.addAttribute("owner", owner);
		model.addAttribute("writer", writer);
		
		model.addAttribute("home", home);
		
		model.addAttribute("postList", postFormList);
		model.addAttribute("homeId", homeId);
		
		return "homes/selectHome";
	}	
	
	// 게시물 등록 폼 조회
	@GetMapping("/posts/{homeId}/new")
	public String createPostForm(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable("homeId")Long homeId, Model model) {
		
		model.addAttribute("homeId", homeId);
		
		// 로그인한 사용자
		User writer = userService.findBynick(principal);
		model.addAttribute("writer", writer);
		
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
	public String updatePostForm(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable("homeId")Long homeId, @PathVariable("postId")Long postId, Model model) {
		
		PostForm post = postService.findById(postId);
		
		// 로그인한 사용자
		User writer = userService.findBynick(principal);
		model.addAttribute("writer", writer);
		
		model.addAttribute("post", post);
		
		return "posts/updatePostForm";
	}
	
	
	// 게시물 수정
	@PostMapping("/posts/{homeId}/update/{postId}")
	public String updatePost (@PathVariable("homeId")Long homeId, @PathVariable("postId")Long postId,
			PostFormFile form, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		
		
		Attach attach = fileStore.storeFile(form.getATTF_OBJ());
		
		// 새로운 파일 있는 경우
		if(attach!=null) {
			// 기존 파일 삭제
			fileStore.deleteStoreFile(Optional.ofNullable(postService.findById(postId).getATTF_OBJ()));
			form.setATTF_ID(postService.createAttach(attach).getATTF_ID());
		}
		
		// 새로운 파일 없음 & 기존 파일 삭제하는 경우
		if(form.isDeleteAttach() && form.getATTF_ID()!=null) { // 기존파일 삭제
			System.out.println("form.isDeleteAttach() = " + form.isDeleteAttach() + " / form.getATTF_ID() = " + form.getATTF_ID());
			fileStore.deleteStoreFile(attachService.findById(form.getATTF_ID()));
			form.setATTF_ID(null);
		}

		form.setPOST_HOME(homeId);

		// DB에 게시물 저장
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