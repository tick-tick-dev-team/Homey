package com.ticktack.homey.repository;

import org.junit.jupiter.api.Test;

import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.repository.comment.MemoryCommRepository;

public class MemoryCommentRepositoryTest {

	MemoryCommRepository repository = new MemoryCommRepository();
	
	@Test
	void 댓글생성() {
		// given
		Comment comm = new Comment();
		comm.setPostId(1L);
		comm.setCommWriter("popdo");
		comm.setCommCont("댓글 테스트2");
		
		// when
		Comment result = repository.commInsert(comm);
		
		// then
		System.out.println(result.toString());
	}
	
}
