package com.ticktack.homey.repository.user;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.ticktack.homey.domain.User;

public class JpaUserRepository implements UserRepository {
	
	private final EntityManager em;

	public JpaUserRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public User createUser(User user) {
		return null;
	}

	@Override
	public Optional<User> findByNick(String usernick) {
		return null;
	}

	@Override
	public List<User> findUsers() {
		return null;
	}

	@Override
	public Optional<User> findById(Long userid) {
		return Optional.ofNullable(em.find(User.class, userid));
	}

	@Override
	public User updateUser(User user) {
		return null;
	}

	@Override
	public void deleteUser(Long userid) {
		
	}

	@Override
	public Optional<User> findByPass(String userpass) {
		return null;
	}

	@Override
	public List<User> findAll() {
		List<User> result = em.createQuery("select u from User u ", User.class)
				.getResultList();
		return result;
	}

}
