package com.ticktack.homey.repository.home;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.ticktack.homey.domain.Home;

public class JpaHomeRepository implements HomeRepository  {

	private EntityManager em;
	
	public JpaHomeRepository(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Home createHome(Home home) {
		em.persist(home);
		return home;
	}

	@Override
	public List<Home> findHomes() {
		return em.createQuery("select h from Home h", Home.class)
				.getResultList();
	}

	@Override
	public Optional<Home> findById(Long homeid) {
		Home home = em.find(Home.class, homeid);
		return Optional.ofNullable(home);
	}

	@Override
	public Home updateHome(Home home) {
		// https://dev-troh.tistory.com/151
		//엔티티 매니저는 별도의 update 메서드를 제공하지 않음
		//jpa는 트랜잭션 범위에서 엔티티 객체의 상태가 변경되면 이를 트랜잭션 커밋 시점에 반영
		return null;
	}

}
