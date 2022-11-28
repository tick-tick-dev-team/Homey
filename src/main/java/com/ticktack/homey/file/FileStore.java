package com.ticktack.homey.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.service.AttachService;


@Component
public class FileStore {

    @Value("${file.dir}")
	private String fileDir;
    
    private String tmpFileDir = System.getProperty("java.io.tmpdir");

	private final AttachService attachService;

	public FileStore(AttachService attachService) {
		super();
		this.attachService = attachService;
	}

    // 파일 이름 입력받아 전체 경로 반환
    public String getFullPath(String filename) {
		return fileDir + filename;
	}
    
    public String getTmpFullPath(String filename) {
		return tmpFileDir + filename;
	}

    // 파일 정보 하나 반환
	public Attach storeFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		if(multipartFile.isEmpty()) {
			return null;
		}
		
		String originalFileName = multipartFile.getOriginalFilename();
		String storeFileName = createStoreFileName(originalFileName);

		multipartFile.transferTo(new File(getFullPath(storeFileName)));
		
		Attach attach = new Attach();
		
		attach.setATTF_REALNM(originalFileName);
		attach.setATTF_SERNM(storeFileName);
		attach.setATTF_ROUTE(getFullPath(storeFileName));
		attach.setATTF_EXE(extractExt(originalFileName));
		attach.setATTF_SIZE(multipartFile.getSize());
		
		return attach;	
	}

    private String extractExt(String originalFileName) {
		int pos = originalFileName.lastIndexOf(".");
		return originalFileName.substring(pos+1);
	}
	
	private String createStoreFileName(String originalFileName) {
		// 원본 파일명을 서버용 파일명으로 변환
		// uuid 사용 + 확장자는 보존
		String uuid = UUID.randomUUID().toString();
		String ext = extractExt(originalFileName);
		
		return uuid + "." + ext;
	}

	// 로컬 파일 삭제
	public void deleteStoreFile (Optional<Attach> attach) throws IOException{
		attach.ifPresent(a -> {
			System.out.println("deleteStoreFile : " + a.getATTF_ROUTE() + " 삭제 시작");
			Path path = Paths.get(a.getATTF_ROUTE());
			try {
				Files.deleteIfExists(path); // 로컬 폴더의 파일 삭제
				attachService.deleteAttach(a.getATTF_ID()); // DB의 attach 정보 삭제
				System.out.println("deleteStoreFile : " + a.getATTF_ROUTE() + " 삭제 완료");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("deleteStoreFile 에러 : " + e.getMessage());
			}
		});
	}
	
	// multipartFile 에서 임시파일 생성해 리턴
	public String storeTmpFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		if(multipartFile.isEmpty()) {
			return null;
		}
		
		String originalFileName = multipartFile.getOriginalFilename();
		multipartFile.transferTo(new File(getTmpFullPath(originalFileName)));
		return originalFileName;
	}
	
	
}
