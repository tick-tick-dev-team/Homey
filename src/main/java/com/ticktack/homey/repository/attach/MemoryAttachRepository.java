package com.ticktack.homey.repository.attach;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ticktack.homey.domain.Attach;

public class MemoryAttachRepository implements AttachRepository{
	
	private static Map<Long, Attach> store = new HashMap<>();
	private Long sequence = 0L;

	@Override
	public Attach save(Attach attach) {
		attach.setATTF_ID(++sequence);
		store.put(attach.getATTF_ID(), attach);
		return store.get(attach.getATTF_ID());
	}

	@Override
	public Optional<Attach> findById(Long attachId) {
		return Optional.ofNullable(store.get(attachId));
	}

	@Override
	public void delete(Long attachId) {
		store.remove(attachId);		
	}
	
	public void clearStroe() {
		store.clear();
	}

}
