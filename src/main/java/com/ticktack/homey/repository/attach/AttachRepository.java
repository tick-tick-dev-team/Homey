package com.ticktack.homey.repository.attach;


import java.util.Optional;

import com.ticktack.homey.domain.Attach;

public interface AttachRepository {
	
	// 첨부파일 저장
	Attach save(Attach attach);
	
	// 첨부파일 id로 첨부파일 1개 조회
	Optional<Attach> findById(Long attachId);
	
	// 첨부파일 1개 삭제
	void delete(Long attachId);
	
}