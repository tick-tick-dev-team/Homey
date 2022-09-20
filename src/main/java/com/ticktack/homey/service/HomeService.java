package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Home;

public interface HomeService {
	//home 생성
	public Home createHome(Home home);
	//home 전체조회
	public List<Home> findByHome(Long homeId);
	//home 상세조회
	public Optional<Home> findById(Long homeId);
	//home 수정
	public Home updateHome(Home home);
}
