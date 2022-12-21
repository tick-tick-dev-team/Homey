package com.ticktack.homey.service.bgm;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.ticktack.homey.repository.bgm.BgmRepository;

@Transactional
public class BgmServiceImpl implements IBgmService{
    private final BgmRepository bgmRepository;
	
	public BgmServiceImpl(BgmRepository bgmRepository) {
		this.bgmRepository = bgmRepository;
	}

    @Override
    public Optional<Object> findById(String bmg_id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
}
