package com.ticktack.homey.repository.home;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Home;

public interface HomeRepository {
	
	//Home 생성
	Home createHome(Home home);
	
	//home 전체조회
	List<Home> findHomes();
	
	//home 상세조회
	Optional<Home> findById(Long homeId);
	
	//home 수정
	Home updateHome(Home home);

}
