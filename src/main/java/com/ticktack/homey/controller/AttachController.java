package com.ticktack.homey.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.file.FileStore;
import com.ticktack.homey.service.AttachService;
import com.ticktack.homey.service.PostService;
import com.ticktack.homey.service.UserService;

@Controller
public class AttachController {
	
	private final UserService userService;
	private final PostService postService;
	private final AttachService attachService;
	
	// 파일 처리 관련 클래스
	private final FileStore fileStore;
	
	public AttachController(UserService userService, PostService postService, AttachService attachService, 
			FileStore fileStore) {
		super();
		this.userService = userService;
		this.postService = postService;
		this.attachService = attachService;
		this.fileStore = fileStore;
	}
	
	// 첨부파일 삭제
	@DeleteMapping("/attach/{attachId}")
	public boolean deleteAttach(@PathVariable Long attachId) {

		System.out.println("attachController - deleteAttach attachId = " + attachId);

		Optional<Attach> attach = attachService.findById(attachId);
		if(attach.isPresent()) {
			attachService.deleteAttach(attach.get().getATTF_ID());
			return true;
		} else {
			return false;
		}
	}
	
	
	// 첨부 이미지 조회
	@ResponseBody
	@GetMapping("/images/{filename}")
	public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
		
		// file:D:/practice/file/diec-e93k.png
		return new UrlResource("file:" + fileStore.getFullPath(filename));
	}
	
	// 첨부파일 다운로드
	@GetMapping("/attach/{postId}")
	public ResponseEntity<Resource> downloadAttach(@PathVariable Long postId) throws MalformedURLException {

		Attach attach = postService.findById(postId).getATTF_OBJ();

		String storeFileName = attach.getATTF_SERNM();
		String originalFileName = attach.getATTF_REALNM();
		
		UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
		System.out.println("uploadFileName=" + originalFileName);
		
		// 한글 깨짐 방지 UTF-8 인코딩
		String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
		
		// header 정보 추가 안하면 url은 파일을 보여주기만 함
		String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(urlResource);
	}
	
	// 임시파일 업로드
	// 게시물 등록
	@ResponseBody
	@PostMapping("/tmp/new")
	public Resource createTmp (MultipartFile file, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		System.out.println("attachController : createTmp");
		
		// 임시 파일 저장
		Optional.ofNullable(file).ifPresent(f -> {
			System.out.println("파일명 : " + f.getOriginalFilename());
			try {
				fileStore.storeTmpFile(f);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return new UrlResource("file:" + fileStore.getTmpFullPath(file.getOriginalFilename()));
	}
	
	// 임시파일 업로드
	// 프로필 등록
	@ResponseBody
	@PostMapping("/users/{userId}/profile")
	public Attach createTmp2 (@PathVariable Long userId, MultipartFile file, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		System.out.println("attachController : createTmp2");
		
		// 임시 파일 저장
		if(Optional.ofNullable(file).isPresent()) {
			
			profileCheck(userId); 
			Attach attach = fileStore.storeFile(file);
			
			// attach db에 저장
			attachService.createAttach(attach);
			// user의 프로필로 저장
			Optional<User> user = userService.findById(userId);
			user.ifPresent(u -> {
				u.setAttf_id(attach.getATTF_ID());
				userService.updateUser(u);
			});
			return attach;
		}
		return null;
	}
	
	/*
	 * 프로필 파일 리셋 및 리셋 결과값 - 10.08 popdo 추가
	 * */ 
	@ResponseBody
	@PostMapping("/users/{userId}/profileReset/{attfId}")
	public boolean profileReset (@PathVariable Long userId, @PathVariable Long attfId, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {		
		System.out.println("----------------attachController : profileReset");
		profileCheck(userId);
		
		// 파일 존재여부 확인
		Optional<Attach> deleteAttach = attachService.findById(attfId);
		if(deleteAttach.isPresent()) {
			return false;
		} else {
			return true;
		}

	}
	
	/*
	 * 기존 파일 있는지 확인 삭제 처리 - 10.08 popdo 추가
	 * --> 해당 메서드를 어디에 정의해야 할지 다같이 논의 필요
	 * */
	public void profileCheck(Long userId) {
		System.out.println("----------------attachController : profileCheck");
		Long deleteAttfId;

		Optional<User> user = userService.findById(userId);
		if(user.get().getAttf_id()!= null) {
			deleteAttfId = user.get().getAttf_id();
			user.get().setAttf_id(null);
			Optional<User> test = Optional.ofNullable(userService.updateUser(user.get()));
			if(test.get().getAttf_id() == null) {
				Optional<Attach> deleteAttach = attachService.findById(deleteAttfId);
				if(deleteAttach.isPresent()) {
					attachService.deleteAttach(deleteAttach.get().getATTF_ID());
					try {
						fileStore.deleteStoreFile(deleteAttach);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("--> [Error] deleteAttach.isPresent() : 기존파일이 존재하지 않습니다.");
				} 
			} else {
				System.out.println("--> [Error] test.get().getAttf_id() == null : user의 기존 파일 null 처리가 정상적으로 처리되지 않았습니다.");
			}		
		}
	}

}
