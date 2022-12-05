package com.ticktack.homey.repository.home;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.ticktack.homey.domain.Home;

@Transactional
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
	public List<Home> findByHomes() {
		return em.createQuery("select h from Home h where h.homeuse=:homeuse", Home.class)
				.setParameter("homeuse", "Y")
				.getResultList();
	}

	@Override
	public Optional<Home> findById(Long homeid) {
		Home home = em.find(Home.class, homeid);
		return Optional.ofNullable(home);
	}

	@Override
	public Home updateHome(Home home) {
		/*변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 사용하면 모든 속성이 변경된다. 
		병합시 값이 없으면 null로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체한다.)
		병합은 편리하게 사용할 수 있다는 장점이 있지만, 모든 필드를 교체한다는 위험성 때문에 사용을 지양해야 한다.
		출처: https://leveloper.tistory.com/37 [꾸준하게:티스토리]
*/		
		em.merge(home);
		
		return home;
	}

	@Override
	public Optional<Home> findByUserId(Long userId) {
		// user_id로 Home 정보 반환
		Home result = em.createQuery("select h from Home h where h.userid=:userId", Home.class)
				.setParameter("userId", userId)
				.getSingleResult();
		return Optional.ofNullable(result);
	}

}
