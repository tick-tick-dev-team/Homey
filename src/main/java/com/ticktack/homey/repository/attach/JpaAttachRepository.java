//package com.ticktack.homey.repository.attach;
//
//import java.util.Optional;
//
//import javax.persistence.EntityManager;
//
//import com.ticktack.homey.domain.Attach;
//
//public class JpaAttachRepository implements AttachRepository {
//	
//	private final EntityManager em;
//
//	public JpaAttachRepository(EntityManager em) {
//		super();
//		this.em = em;
//	}
//
//	@Override
//	public Attach save(Attach attach) {
//		em.persist(attach);
//		return attach;
//	}
//
//	@Override
//	public Optional<Attach> findById(Long attachId) {
//		return Optional.ofNullable(em.find(Attach.class, attachId));
//	}
//
//	@Override
//	public void delete(Long attachId) {
//		em.createQuery("delete a from Attach a where a.attachId = :ATTF_ID")
//		.setParameter("attachId", attachId)
//		.executeUpdate();		
//	}
//
//}
