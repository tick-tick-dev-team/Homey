package com.ticktack.homey.service;

import java.util.Optional;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.repository.attach.AttachRepository;

public class AttachServiceImpl implements AttachService{
	
	private final AttachRepository attachRepository;

	public AttachServiceImpl(AttachRepository attachRepository) {
		super();
		this.attachRepository = attachRepository;
	}

	@Override
	public Optional<Attach> findById(Long attf_id) {
		return attachRepository.findById(attf_id);
	}

	@Override
	public Attach createAttach(Attach attach) {
		return attachRepository.save(attach);
	}

	@Override
	public Long deleteAttach(Long attf_id) {
		attachRepository.delete(attf_id);
		return attf_id;
	}

}
