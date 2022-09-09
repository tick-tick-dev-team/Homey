package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Home;
import com.ticktack.homey.repository.home.HomeRepository;

public class HomeServiceImpl implements HomeService{
	
	private final HomeRepository homeRepository;
	
	public HomeServiceImpl(HomeRepository homeRepository) {
		this.homeRepository = homeRepository;
	}
	//home 생성
	public Home create(Home home) {
		return homeRepository.create(home);
	}
	
	//home 전체조회
	@Override
	public List<Home> findByHome(Long homeId){
		return homeRepository.findByHome(homeId);
	}
	//home 상세조회
	@Override
	public Optional<Home> findById(Long homeId){
		return homeRepository.findById(homeId);
	}
	//home 수정
	@Override
	public Home updateHome(Home home) {
		
		return homeRepository.update(home);
		
	}
}
