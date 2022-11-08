package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.UHList;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.repository.attach.AttachRepository;
import com.ticktack.homey.repository.home.HomeRepository;

public class HomeServiceImpl implements HomeService{
	
	private final HomeRepository homeRepository;
	private AttachRepository attachRepository;
	
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
	public List<Home> findHomes(){
		return homeRepository.findHomes();
	}
	
	//y인 홈 조회
	@Override
	public List<Home> findByHomes(){
		return homeRepository.findByHomes();
	}
	
	//home 상세조회
	@Override
	public Optional<Home> findById(Long homeId){
		return homeRepository.findById(homeId);
	}
	//home 수정
	@Override
	public Home updateHome(Home home) {
		return homeRepository.updateHome(home);
		
	}

	// user_id로 home 조회 - 10.15 popdo 추가
	@Override
	public Optional<Home> findByUserId(Long userId) {
		return homeRepository.findByUserId(userId);
	}


}
