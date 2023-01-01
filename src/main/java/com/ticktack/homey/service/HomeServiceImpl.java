package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Home;
import com.ticktack.homey.domain.HomeForm;
import com.ticktack.homey.repository.attach.AttachRepository;
import com.ticktack.homey.repository.home.HomeRepository;

public class HomeServiceImpl implements HomeService {

	private final HomeRepository homeRepository;
	private final AttachRepository attachRepository;

	public HomeServiceImpl(HomeRepository homeRepository, AttachRepository attachRepository) {
		this.homeRepository = homeRepository;
		this.attachRepository = attachRepository;
	}

	// home 생성
	public Home createHome(Home home) {

		// home.setHomename(user.getUsernick()+"의 집"); 은 UserController에서 진행
		home.setHomeuse("Y");
		return homeRepository.createHome(home);
	}

	// home 전체조회
	@Override
	public List<Home> findHomes() {
		return homeRepository.findHomes();
	}

	// y인 홈 조회
	@Override
	public List<Home> findByHomes() {
		return homeRepository.findByHomes();
	}

	// home 상세조회
	@Override
	public Optional<Home> findById(Long homeId) {
		return homeRepository.findById(homeId);
	}

	// home 수정
	@Override
	public Home updateHome(Home home) {
		return homeRepository.updateHome(home);

	}

	@Override
	public HomeForm findByHFId(Long homeId) {
		// homeId에 따른 home정보 HomeForm형식의 form에 담기
		HomeForm form = homeRepository.findById(homeId).get().getFormFromHome();

		// 첨부파일 정보 가져오기
		Optional<Long> attfId = Optional.ofNullable(form.getAttfid());
		attfId.ifPresent(id -> {
			attachRepository.findById(id).ifPresent(att -> {
				form.setAttf_obj(att);
			});
		});

		return form;
	}

	// user_id로 home 조회 - 10.15 popdo 추가
	@Override
	public Optional<Home> findByUserId(Long userId) {
		return homeRepository.findByUserId(userId);
	}

	
	
	// 첨부파일 정보 생성
	@Override
	public Attach createAttach(Attach attach) {
		return attachRepository.save(attach);
	}
	
	// 첨부파일 정보 삭제
	@Override
	public Long deleteAttach(Long attf_id) {
		attachRepository.delete(attf_id);
		return attf_id;
	}
	
	@Override
	public List<Home> findByKeyword(String keyword){
		return homeRepository.findByKeyword(keyword);
	}
}
