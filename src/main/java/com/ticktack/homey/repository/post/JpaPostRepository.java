package com.ticktack.homey.repository.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.ticktack.homey.domain.Post;

public class JpaPostRepository implements PostRepository {
	
	private final EntityManager em;

	public JpaPostRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	@Transactional
	public Post save(Post post) {
		// id, 생성일, 수정일 넣고 저장
		post.setPOST_DATE(LocalDateTime.now());
		post.setPOST_UPDATE(post.getPOST_DATE());
		em.persist(post);
		return post;
	}

	@Override
	public List<Post> findByHomeId(Long homeId) {
		// 모든 post 중 homeId가 같은 것만 골라내어 리스트 형태로 반환
		List<Post> result = em.createQuery("select p from Post p where p.POST_HOME=:homeId order by POST_UPDATE desc", Post.class)
				.setParameter("homeId", homeId)
				.getResultList();
		return result;
	}

	@Override
	@Transactional
	public Post update(Post post) {
		post.setPOST_UPDATE(LocalDateTime.now());
		em.merge(post);
		return post;
	}

	@Override
	@Transactional
	public void delete(Long postId) {
		em.createQuery("delete from Post p where p.POST_ID = :postId")
		.setParameter("postId", postId)
		.executeUpdate();
	}

	@Override
	public boolean existsById(Long postId) {
		return (Optional.ofNullable(em.find(Post.class, postId)).isPresent());
	}

	@Override
	public List<Post> findAll() {
		return em.createQuery("select p from Post p", Post.class)
				.getResultList();
	}

	@Override
	public Optional<Post> findById(Long postId) {
		return Optional.ofNullable(em.find(Post.class, postId));
	}

	@Override
	public Optional<Long> findAttfIdById(Long postId) {
		return Optional.ofNullable(em.find(Post.class, postId).getATTF_ID());
	}

}
