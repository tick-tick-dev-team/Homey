package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.UHList;


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
	
	//user_id로 home 조회 - 10.15 popdo 추가
	public Optional<Home> findByUserId(Long userId);
	
}
