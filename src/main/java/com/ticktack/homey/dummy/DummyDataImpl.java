package com.ticktack.homey.dummy;

import java.util.List;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.domain.Post;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.service.AttachService;
import com.ticktack.homey.service.CommentService;
import com.ticktack.homey.service.PostService;
import com.ticktack.homey.service.UserService;

public class DummyDataImpl implements DummyData{
	
	// userService 추가 필요
	private final UserService userService;
	private final PostService postService;
	private final AttachService attachService;
	private final CommentService commentService;

	public DummyDataImpl(UserService userService, PostService postService, AttachService attachService, CommentService commentService) {
		super();
		this.userService = userService;
		this.postService = postService;
		this.attachService = attachService;
		this.commentService = commentService;
	}

	private String[] usernames = {"224", "사장", "팝도"};
	private String[] postContents = {"게시물1", "게시물2", "게시물3"};
	private String[] filenames = {"apple.jpg", "banana.jpg", "watermelon.jpg"};
	private String fileRoute = "/src/main/resources/uploads/";

	@Override
	public void setUsers() {
//		List<User> result = userService.findByUser();
//		
//		// 추후 진행
//		if(result.size()==0) {
//			System.out.println("더미 사용자 삽입 시작");
//		}	
		
	}
	@Override
	public User getUser(int num) {
		User user = new User();
		user.setUser_id(num+0L);
		user.setUsernick(usernames[num-1]);
		return user;
	}

	// usernames 개수만큼 homeId 생성 -> 하나의 홈에 postContents 개수만큼 게시물 생성
	@Override
	public void setPosts() {
		List<Post> result = postService.findAll();
		
		if(result.size()==0) {
			System.out.println("더미 게시물 삽입 시작");
			for (int i=0; i<usernames.length; i++) {
				for (String content: postContents) {
					Post post = new Post();
					Long userId = i+1L;
					
					post.setPOST_HOME(userId);
					post.setPOST_CONT(usernames[i] + "의 " + content);
					post.setPOST_WRITER(userId);

					postService.createPost(post);
				}
			}
		}
	}

	// postId 전달하면 해당 게시물에 usernames 개수만큼 댓글 생성
	@Override
	public void setComments(Long postId) {
		System.out.println("더미 댓글 삽입 시작");
		
		for (int i = 0; i<usernames.length; i++) {
			Comment comment = new Comment();
			Long userId = i + 1L;
			comment.setPostId(postId);
			comment.setCommCont(usernames[i] + "님의 댓글");
			comment.setCommWriter(userId.toString());
			
			commentService.commInsert(comment);
		}
	}
	
	@Override
	public void setReplyComments(Long postId) {
		System.out.println("더미 대댓글 삽입 시작");
		Comment tmp = new Comment();
		tmp.setPostId(postId);
		List<Comment> comments = commentService.commAllList(tmp);
		
		if(comments!=null) {
			for (Comment comment : comments) {
				if(comment.getCommId()%2!=0) {
					Comment reply = new Comment();
					
					reply.setPostId(comment.getPostId());
					reply.setCommCont("-->" + comment.getCommCont() + "에 대한 대댓글");
					reply.setCommWriter(comment.getCommWriter());
					reply.setCommUpid(comment.getCommId());
					
					commentService.commInsert(reply);
				}
			}
		}

	}

	// postId 전달하면 해당 게시물에 1개의 첨부파일 정보 생성
	@Override
	public Long setAttach(Long postId) {
		System.out.println("더미 첨부파일정보 삽입 시작");
		
		Attach attach = new Attach();
		int Id = (postId.intValue() - 1)%3;
		
		attach.setATTF_REALNM(filenames[Id]);
		attach.setATTF_SERNM(filenames[Id] + Id);
		attach.setATTF_ROUTE(fileRoute + attach.getATTF_SERNM());
		attach.setATTF_EXE(filenames[Id].split("\\.")[1]);
		attach.setATTF_SIZE(filenames[Id].length() + 0L);
		
		attachService.createAttach(attach);
		//System.out.println(attach.toString());
		
		return attach.getATTF_ID();
	}

}
