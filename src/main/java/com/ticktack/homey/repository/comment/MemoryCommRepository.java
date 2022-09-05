package com.ticktack.homey.repository.comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.domain.Post;

public class MemoryCommRepository implements CommentRepository {

	private static Map<Long, Comment> store = new HashMap<>();
	private static Map<Long, Comment> replyStore = new HashMap<>();

	private Long commSequence = 0L;
	// private Long replySequence = 0L;
	
	/**
	 * 게시글의 댓글 전체 조회
	 */
	@Override
	public List<Comment> commAllList(Comment comm) {
		List<Comment> commList = new ArrayList<Comment>();
		List<Comment> replyList = new ArrayList<Comment>();
		List<Comment> resultList = new ArrayList<Comment>();

		commList = store.values().stream().filter(comment -> comment.getPostId().equals(comm.getPostId()))
				.collect(Collectors.toList());

		replyList = replyStore.values().stream().filter(comment -> comment.getPostId().equals(comm.getPostId()))
				.collect(Collectors.toList());

		for (int i = 0; i < commList.size(); i++) {
			resultList.add(commList.get(i));
			for (int j = 0; j < replyList.size(); j++) {
				if (commList.get(i).getCommId() == replyList.get(j).getCommUpid()) {
					resultList.add(replyList.get(j));
				}
			}
		}

		return resultList;
	}

	/**
	 * 댓글, 답글 존재 여부
	 */
	@Override
	public boolean commExist(Comment comm) {
		boolean result = false;
		if (comm.getCommUpid() != null) {
			result = replyStore.containsKey(comm.getCommUpid());
		} else {
			result = store.containsKey(comm.getCommId());
		}
		return result;
	}

	/**
	 * 댓글, 답글 등록
	 */
	@Override
	public Comment commInsert(Comment comm) {

		Comment result = new Comment();

		comm.setCommDate(new Date());
		comm.setCommUdate(new Date());
		comm.setCommId(++commSequence);

		if (comm.getCommUpid() != null) {
			replyStore.put(comm.getCommId(), comm);
			result = replyStore.put(comm.getCommId(), comm);
		} else {
			store.put(comm.getCommId(), comm);
			result = store.put(comm.getCommId(), comm);
		}

		return result;
	}

	/**
	 * 댓글, 답글 수정
	 */
	@Override
	public Comment commUpdate(Comment comm) {

		comm.setCommUdate(new Date());
		
		if (comm.getCommUpid() != null) {
			replyStore.put(comm.getCommId(), comm);
			return replyStore.get(comm.getCommId());
		} else {
			store.put(comm.getCommId(), comm);
			System.out.println("sotre 체크"+store.get(comm.getCommId()));
			return store.get(comm.getCommId());
		}
	}

	/**
	 * 댓글, 답글 삭제
	 */
	@Override
	public boolean commDelete(Comment comm) {
		boolean result = false;
		if (comm.getCommUpid() != null) {
			replyStore.remove(comm.getCommId());
		} else {
			store.remove(comm.getCommId());
			// 댓글 삭제시 답글 전체 삭제
			Set<Entry<Long, Comment>> entrySet = replyStore.entrySet();
			for (Entry<Long, Comment> entry : entrySet) {
				if (entry.getValue().getCommUpid() == comm.getCommId()) {
					replyStore.remove(entry.getKey());
				}
			}
		}
		return result;
	}
	
	/**
	 * 댓글, 답글 한건 조회
	 * */
	@Override
	public Optional<Comment> findById(Comment comm) {
		
		if (comm.getCommUpid() != null) {
			return Optional.ofNullable(replyStore.get(comm.getCommId()));
		}else {
			return Optional.ofNullable(store.get(comm.getCommId()));
		}
	}
	
	
	public void clearStore() {
		store.clear();
	}
	
	public void clearReplyStore() {
		replyStore.clear();
	}


	

	
}
