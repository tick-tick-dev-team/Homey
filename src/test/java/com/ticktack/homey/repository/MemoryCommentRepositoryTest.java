package com.ticktack.homey.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.repository.comment.MemoryCommRepository;

public class MemoryCommentRepositoryTest {

	MemoryCommRepository repository = new MemoryCommRepository();
	
	@AfterEach
	public void afterEach() {
		repository.clearStore();
		repository.clearReplyStore();
	}
	
/*	@Test
	void 댓글생성() {
		// given
		Comment comm = new Comment();
		comm.setPostId(1L);
		comm.setCommWriter("popdo");
		comm.setCommCont("댓글 테스트2");
		
		// when
		Comment result = repository.commInsert(comm);
		
		// then
		assertThat(result).isEqualTo(comm);
	}
	
	@Test
	void 답글생성() {
		// given
		Comment comm1 = new Comment();
		comm1.setPostId(1L);
		comm1.setCommWriter("ceo");
		comm1.setCommUwriter("ceo");
		comm1.setCommCont("댓글1");
		
		Comment comm2 = new Comment();
		comm2.setPostId(1L);
		comm2.setCommWriter("popdo");
		comm2.setCommUwriter("popdo");
		comm2.setCommCont("감격! 드디어 답글 완성!");
		comm2.setCommUpid(1L);
		
		// when
		Comment result = repository.commInsert(comm1);
		Comment result2 = repository.commInsert(comm2);
		
		// then
		List<Comment> resultAllList = repository.commAllList(comm1);
		
		for(int i=0; i < resultAllList.size(); i++) {
			if(resultAllList.get(i).getCommUpid() != null) {
				System.out.println(" └ "+ resultAllList.get(i).getCommCont());
			} else {
				System.out.println(resultAllList.get(i).getCommCont());
			}
		}
	}*/
	
	
	@Test
	void 댓글수정() {
		// given
		Comment comm1 = new Comment();
		comm1.setPostId(1L);
		comm1.setCommWriter("ceo");
		comm1.setCommUwriter("ceo");
		comm1.setCommCont("댓글1");
		repository.commInsert(comm1);
	

		comm1.setCommCont("댓글1 수정완료");
		
		// when
		repository.commUpdate(comm1);
		Comment result = repository.findById(comm1).get();
		
		// then
		System.out.println(result.toString());	
	}
	
	@Test
	void 답글수정() {
		
	}
}
