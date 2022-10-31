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

	//main을 위한 메소드
/*	@Override
	public List<UHList> findMain() {
		//집 전체 조회 + 첨부파일
		List<Home> homes = homeRepository.findHomes();
		
		List<UHList> main = homes.stream().map(home -> home.getFromHome()).collect(Collectors.toList());
		
		main.forEach(form -> {
			//첨부파일 정보 가져오기
			Optional<Long> attfId = Optional.ofNullable(form.getATTF_ID());
			attfId.ifPresent(id -> {
				attachRepository.findById(id).ifPresent(att -> {
					form.setATTF_OBJ(att);
				});
			});
		}
		);
		
		return main;
	}*/
}
