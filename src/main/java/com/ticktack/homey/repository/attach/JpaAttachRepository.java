package com.ticktack.homey.repository.attach;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.ticktack.homey.domain.Attach;

public class JpaAttachRepository implements AttachRepository {
	
	private final EntityManager em;

	public JpaAttachRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	@Transactional
	public Attach save(Attach attach) {
		em.persist(attach);
		return attach;
	}

	@Override
	public Optional<Attach> findById(Long attachId) {
		return Optional.ofNullable(em.find(Attach.class, attachId));
	}

	@Override
	@Transactional
	public void delete(Long attachId) {
		em.createQuery("delete from Attach a where a.ATTF_ID = :attachId")
		.setParameter("attachId", attachId)
		.executeUpdate();		
	}

}
