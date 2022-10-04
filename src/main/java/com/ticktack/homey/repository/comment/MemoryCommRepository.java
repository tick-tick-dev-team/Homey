package com.ticktack.homey.repository.comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ticktack.homey.domain.Comment;

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
		if (comm.getCommUpid() != comm.getCommId()) {
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
		comm.setCommUwriter(comm.getCommWriter());
		comm.setCommUdate(new Date());
		comm.setCommId(++commSequence);


		if (comm.getCommUpid() != comm.getCommId()) {
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
		
		if (comm.getCommUpid() != comm.getCommId()) {
			replyStore.put(comm.getCommId(), comm);
			return replyStore.get(comm.getCommId());
		} else {
			store.put(comm.getCommId(), comm);
			return store.get(comm.getCommId());
		}
	}

	/**
	 * 댓글, 답글 삭제 - 22.09.14 수정
	 */
	@Override
	public boolean commDelete(Comment comm) {
		boolean result = false;
		if (comm.getCommUpid() != comm.getCommId()) {
			replyStore.remove(comm.getCommId());
			result = true;
		} else {
			store.remove(comm.getCommId());
			result = true;
			// 댓글 삭제시 답글 전체 삭제 - 22.09.19 수정 (HaspMap을 돌 때 안에 삭제를 하면 next 할때 map size 값이 달라져서 오류 발생)
			ArrayList<Long> keyList = new ArrayList<Long>();
			Iterator<Long> keys = replyStore.keySet().iterator();
			while(keys.hasNext()) {
				Long key = keys.next();
				if(replyStore.get(key).getCommUpid() == comm.getCommId()) {
					keyList.add(key);
				}
			}
			if(keyList.size() > 0 ) {
				for(Long i : keyList) {
					replyStore.remove(i);
				}
			}
		}
		return result;
	}
	
	/**
	 * 댓글, 답글 한건 조회 - 22.09.13 수정
	 * */
	@Override
	public Optional<Comment> findById(Comment comm) {
		
		Optional<Comment> result = Optional.ofNullable(store.get(comm.getCommId()));		
		if(result.isPresent()) {
			// 있음
		} else {
			// 없음
			result = Optional.ofNullable(replyStore.get(comm.getCommId()));
		}
		return result;
			
	}
	
	
	public void clearStore() {
		store.clear();
	}
	
	public void clearReplyStore() {
		replyStore.clear();
	}

	// 게시글의 댓글 모두 조회
	@Override
	public List<Comment> AllList(Long postId) {
		List<Comment> commList = new ArrayList<Comment>();
		commList = store.values().stream().filter(comment -> comment.getPostId().equals(postId))
				.collect(Collectors.toList());
		return commList;
	}
	
	// 게시글의 대댓글 모두 조회
	@Override
	public List<Comment> replyAllList(Long postId) {
		List<Comment> replyList = new ArrayList<Comment>();
		replyList = replyStore.values().stream().filter(comment -> comment.getPostId().equals(postId))
				.collect(Collectors.toList());
		return replyList;
	}

	@Override
	public Comment commUpidUpdate(Comment result) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
}
