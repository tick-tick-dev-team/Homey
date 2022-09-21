package com.ticktack.homey.repository.comment;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey.domain.Comment;

public class JpaCommentRepository implements CommentRepository {

	@Override
	public List<Comment> commAllList(Comment comm) {
		return null;
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
