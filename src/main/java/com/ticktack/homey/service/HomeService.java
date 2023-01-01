package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.HomeForm;

public interface HomeService {
	//home 생성
	public Home createHome(Home home);
	//home 전체조회
	public List<Home> findHomes();
	//Y인 home 조회
	public List<Home> findByHomes();
	//home 상세조회
	public Optional<Home> findById(Long homeId);
	//home 수정
	public Home updateHome(Home home);
	//homeForm 조회
	public HomeForm findByHFId(Long homeId);
	
	//user_id로 home 조회 - 10.15 popdo 추가
	public Optional<Home> findByUserId(Long userId);
	
	// 첨부파일 정보 생성
	public Attach createAttach(Attach attach);	
	
	// 첨부파일 정보 삭제
	public Long deleteAttach(Long attf_id);
	
	//home 조회
	public List<Home> findByKeyword(String keyword);
	
}
