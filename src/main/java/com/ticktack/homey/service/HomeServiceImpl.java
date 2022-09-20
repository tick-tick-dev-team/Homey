package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.repository.home.HomeRepository;

public class HomeServiceImpl implements HomeService{
	
	private final HomeRepository homeRepository;
	
	public HomeServiceImpl(HomeRepository homeRepository) {
		this.homeRepository = homeRepository;
	}
	
	//home 생성
	public Home createHome(Home home) {
		
		//home.setHomename(user.getUsernick()+"의 집"); 은 UserController에서 진행
		home.setHomeuse("Y");
		return homeRepository.createHome(home);
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
		home.setHomename(home.getHomename());
		home.setHomeinst(home.getHomeinst());
		home.setHomethema(home.getHomethema());
		home.setHomeuse(home.getHomeuse());
		
		return homeRepository.updateHome(home);
		
	}
}
