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
	Optional<Home> findById(Long homeid);
	
	//home 수정
	Home updateHome(Home home);
	
	//user_id로 home 조회 - 10.15 popdo 추가
	Optional<Home> findByUserId(Long userId);

}
