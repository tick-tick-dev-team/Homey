package com.ticktack.homey.repository.user;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.ticktack.homey.domain.User;

@Transactional
public class JpaUserRepository implements UserRepository{

	private final EntityManager em;
	
	public JpaUserRepository(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public User createUser(User user) {
		em.persist(user);
		return user;
	}

	@Override
	public Optional<User> findByNick(String usernick) {
		return em.createQuery("select u from User u where u.usernick = :usernick", User.class)
				.setParameter("usernick",usernick)
				.getResultList().stream().findAny();
	}
	
/*	@Override
	public Long countBynick(String usernick) {
		return em.countQuery("select count(u) from User u where u.usernick = :usernick")
				.setParameter("usernick", usernick)
				.getSingleResult();
	}*/
	
	@Override
	public User findBynick(String usernick) {
		return em.createQuery("select u from User u where u.usernick = :usernick", User.class)
				.setParameter("usernick",usernick)
				.getSingleResult();
	}

	@Override
	public List<User> findUsers() {
		return em.createQuery("select u from User u", User.class)
				.getResultList();
	}

	@Override
	public Optional<User> findById(Long userid) {
		User user = em.find(User.class, userid);
		return Optional.ofNullable(user);
	}

	@Override
	public User updateUser(User user) {
		return null;
	}

	@Override
	public void deleteUser(Long userid) {
		
	}

	

	



	
}
