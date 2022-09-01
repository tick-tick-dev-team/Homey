package com.ticktack.homey.repository.comment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ticktack.homey.domain.Comment;

public class MemoryCommRepository implements CommentRepository {
	
	private static Map<Long, Comment> store = new HashMap<>();
	private static Map<Long, Comment> replyStore = new HashMap<>();
	
	private Long commSequence = 0L;
	private Long replySequence = 0L;
	/**
	 * 게시글의 댓글 전체 조회
	 * */
	@Override
	public List<Comment> commAllList(Comment comm) {
		return null;
	}

	/**
	 * 게시글의 댓글 총 갯수
	 * */
	@Override
	public int commAllCount(Comment comm) {
		return 0;
	}

	/**
	 * 댓글의 답글 전체 조회
	 * */
	@Override
	public List<Comment> replyAllList(Comment comm) {
		return null;
	}
	
	/**
	 * 댓글의 답글 총 갯수
	 * */
	@Override
	public int replyAllCount(Comment comm) {
		return 0;
	}
	
	/**
	 * 댓글, 답글 존재 여부
	 * */
	public boolean commExist(Comment comm) {
		boolean result = false;
		if (comm.getCommUpid() == null) {
			result = store.containsKey(comm.getCommId());
		} else {
			result = replyStore.containsKey(comm.getCommUpid());
		}
		
		return result;
	}

	/**
	 * 댓글 등록
	 * */
	@Override
	public Comment commInsert(Comment comm) {
		comm.setCommDate(new Date());
		comm.setCommUdate(new Date());
		comm.setCommId(++commSequence);
		boolean result = commExist(comm);
		if( result) {
			comm.setCommId(++commSequence);
		} else {
			commSequence = 0L;
			comm.setCommId(++commSequence);
		}

		store.put(comm.getCommId(), comm);
		return store.get(comm.getCommId());
	}

	/**
	 * 답글 등록
	 */
	@Override
	public Comment replyInsert(Comment comm) {
		comm.setCommDate(new Date());
		comm.setCommUdate(new Date());
		boolean result = commExist(comm);
		if( result) {
			comm.setCommUpid(++replySequence);
		} else {
			replySequence = 0L;
			comm.setCommUpid(++replySequence);
		}
		
		replyStore.put(comm.getCommUpid(), comm);
		return store.get(comm.getCommUpid());
	}

	/**
	 * 댓글, 답글 수정
	 * */
	@Override
	public Comment commUpdate(Comment comm) {
		return null;
	}

	/**
	 * 댓글, 답글 삭제
	 * */
	@Override
	public int commDelete(Comment comm) {
		return 0;
	}



}
