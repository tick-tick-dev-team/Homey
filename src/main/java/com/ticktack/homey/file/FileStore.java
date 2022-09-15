package com.ticktack.homey.file;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ticktack.homey.domain.Attach;

@Component
public class FileStore {

    @Value("${file.dir}")
	private String fileDir;

    // 파일 이름 입력받아 전체 경로 반환
    public String getFullPath(String filename) {
		return fileDir + filename;
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

}
