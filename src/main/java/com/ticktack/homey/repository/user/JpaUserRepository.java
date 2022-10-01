package com.ticktack.homey.repository.user;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.ticktack.homey.domain.User;

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
		User user = em.find(User.class, usernick);
		return Optional.ofNullable(user);
	}

	@Override
	public User findBynick(String usernick) {
		User user = em.find(User.class, usernick);
		return user;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long userid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<User> findByPass(String userpass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	} 

	
}
