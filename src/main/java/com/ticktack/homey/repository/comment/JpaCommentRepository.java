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
		List<Comment> result = em.createQuery("select c, COALESCE(c.commUpid, c.commId + 0) as c.num from Comment c where c.postId = :postId order by num, comm_id", Comment.class)
				.setParameter("postId", comm.getPostId())
				.getResultList();
		return result;
	}

	@Override
	public Comment commInsert(Comment comm) {
		return null;
	}

	@Override
	public Comment commUpdate(Comment comm) {
		return null;
	}

	@Override
	public boolean commDelete(Comment comm) {
		return false;
	}

	@Override
	public boolean commExist(Comment comm) {
		return false;
	}

	@Override
	public Optional<Comment> findById(Comment comm) {
		return null;
	}

	@Override
	public List<Comment> AllList(Long postId) {
		return null;
	}

	@Override
	public List<Comment> replyAllList(Long postId) {
		return null;
	}



}
