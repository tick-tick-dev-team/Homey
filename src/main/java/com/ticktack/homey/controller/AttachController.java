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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.PostFormFile;
import com.ticktack.homey.dummy.DummyData;
import com.ticktack.homey.file.FileStore;
import com.ticktack.homey.service.AttachService;
import com.ticktack.homey.service.CommentService;
import com.ticktack.homey.service.PostService;

@Controller
public class AttachController {
	
	private final PostService postService;
	private final AttachService attachService;
	
	// 파일 업로드
	private final FileStore fileStore;
	
	public AttachController(PostService postService, AttachService attachService, 
			FileStore fileStore) {
		super();
		this.postService = postService;
		this.attachService = attachService;
		this.fileStore = fileStore;
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
		
		// 임시 파일 저장
		Optional.ofNullable(file).ifPresent(f -> {
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
	
	
	// 임시파일 이미지 조회
	// 게시물 등록
//	@ResponseBody
//	@PostMapping("/tmp")
//	public Resource showTmpImage (MultipartFile file,
//			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
//		
//		Optional.ofNullable(file).ifPresent(f -> fileStore.storeTmpFile(f));
//		
//		return new UrlResource("file:" + fileStore.getTmpFullPath(file.getOriginalFilename()));
//	}	

}
