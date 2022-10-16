package com.ticktack.homey.repository.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

import com.ticktack.homey.domain.Home;

public class MemoryHomeRepository implements HomeRepository {

	private static Map<Long, Home> store = new HashMap<>();
	private static long sequence = 0L;
	
	@Override
	public Home createHome(Home home) {
		
		home.setHomeid(++sequence);
		
		store.put(home.getHomeid(), home);
		return null;
	}
	
	@Override
	public List<Home> findHomes() {
		return new ArrayList<>(store.values());
	}

	@Override
	public Optional<Home> findById(Long homeid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Home updateHome(Home home) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Home> findByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
