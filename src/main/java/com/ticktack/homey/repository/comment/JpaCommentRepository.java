package com.ticktack.homey.repository.comment;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import com.ticktack.homey.domain.Comment;

public class JpaCommentRepository implements CommentRepository {
	
	private final EntityManager em;
	
	public JpaCommentRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Comment> commAllList(Comment comm) {
		List<Comment> result = em.createQuery("select c from Comment c where c.postId = :postId order by commUpid, commId", Comment.class)
				.setParameter("postId", comm.getPostId())
				.getResultList();
		return result;
	}

	@Override
	public Comment commInsert(Comment comm) {
		em.persist(comm);
		return comm;
	}

	@Override
	public Comment commUpdate(Comment comm) {
		em.merge(comm);
		return comm;
	}

	@Override
	public boolean commDelete(Comment comm) {
		em.createQuery("delete from Comment c where c.commId = :commId or c.commUpid = :commId")
		.setParameter("commId", comm.getCommId())
		.executeUpdate();
		return true;
	}

	@Override
	public boolean commExist(Comment comm) {
		return false;
	}

	@Override
	public Optional<Comment> findById(Comment comm) {
		return Optional.ofNullable(em.find(Comment.class, comm.getCommId()));
	}

	@Override
	public List<Comment> AllList(Long postId) {
		return null;
	}

	@Override
	public List<Comment> replyAllList(Long postId) {
		return null;
	}

	/* 댓글 생성 후에 commUpid 넣어주는 메서드  */
	@Override
	public Comment commUpidUpdate(Comment comm) {
		em.merge(comm);
		return comm;
	}


}
