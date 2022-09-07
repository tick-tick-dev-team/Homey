package com.ticktack.homey.service.attach;

import java.util.Optional;

import com.ticktack.homey.domain.Attach;

public interface AttachService {
	
	// 첨부파일 정보 조회
	public Optional<Attach> findById (Long attf_id);
	
	// 첨부파일 정보 생성
	public Attach createAttach(Attach attach);
	
	// 첨부파일 정보 삭제
	public Long deleteAttach(Long attf_id);
}
