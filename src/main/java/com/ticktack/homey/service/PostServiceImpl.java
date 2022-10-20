package com.ticktack.homey.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ticktack.homey.domain.Attach;
import com.ticktack.homey.domain.Comment;
import com.ticktack.homey.domain.CommentImgForm;
import com.ticktack.homey.domain.Post;
import com.ticktack.homey.domain.PostForm;
import com.ticktack.homey.domain.User;
import com.ticktack.homey.repository.attach.AttachRepository;
import com.ticktack.homey.repository.comment.CommentRepository;
import com.ticktack.homey.repository.post.PostRepository;
import com.ticktack.homey.repository.user.UserRepository;

public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final AttachRepository attachRepository;
	private final UserRepository userRepository;

	public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, AttachRepository attachRepository,UserRepository userRepository) {
		super();
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.attachRepository = attachRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<Post> findByHomeId(Long homeId) {
		return postRepository.findByHomeId(homeId);
	}

	@Override
	public PostForm findById(Long postId) {
		PostForm form = postRepository.findById(postId).get().getFormFromPost();
		
		// 첨부파일 정보 가져오기	
		Optional<Long> attfId = Optional.ofNullable(form.getATTF_ID());
		attfId.ifPresent(id -> {
			attachRepository.findById(id).ifPresent(att -> {
				form.setATTF_OBJ(att);
			});
		});

		// 댓글
		Comment comment1 = new Comment();
		comment1.setPostId(form.getPOST_ID());
		List<Comment> comments = commentRepository.commAllList(comment1);
		List<CommentImgForm> commentImg = comments.stream().map(comment -> comment.getFormFromComment()).collect(Collectors.toList());
		
		form.setCOMMENT_LIST(commentImg);
		
		// 작성자 닉네임, 프로필사진 정보
		Long writer_user_id = (form.getPOST_UWRITER()==null) ? form.getPOST_WRITER() : form.getPOST_UWRITER();
		User writer = userRepository.findById(writer_user_id).get();
		
		System.out.println("==================writer id============" + writer_user_id + "========" + writer.getUsernick());
		
		form.setWriterNick(writer.getUsernick());
		
		if(writer.getAttf_id()!=null) {
			form.setWriterProfile(attachRepository.findById(writer.getAttf_id()).get());
		}
		
		return form;
	}

	@Override
	public Post createPost(Post post) {
		return postRepository.save(post);
	}
	
	@Override
	public Attach createAttach(Attach attach) {
		return attachRepository.save(attach);
	}

	@Override
	public Post updatePost(Post post) {
		return postRepository.update(post);
	}

	@Override
	public Long deletePost(Long postId) {
		// 첨부파일이 있으면 그것도 삭제
		Optional<Long>attf_id = findAttfIdById(postId);
		attf_id.ifPresent(id -> attachRepository.delete(id));
		
		postRepository.delete(postId);
		return postId;
	}
	
	// 첨부파일 정보 삭제
	@Override
	public Long deleteAttach(Long attf_id) {
		attachRepository.delete(attf_id);
		return attf_id;
	}

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}
	
	@Override
	public Optional<Long> findAttfIdById(Long postId) {
		return postRepository.findAttfIdById(postId);
	}

	@Override
	public List<PostForm> findAllByHomeId(Long homeId) {
		List<Post> posts = postRepository.findByHomeId(homeId);
		
		List<PostForm> postForms = posts.stream().map(post -> post.getFormFromPost()).collect(Collectors.toList());
		
		postForms.forEach(form -> {
			// 첨부파일 정보 가져오기	
			Optional<Long> attfId = Optional.ofNullable(form.getATTF_ID());
			attfId.ifPresent(id -> {
				attachRepository.findById(id).ifPresent(att -> {
					form.setATTF_OBJ(att);
				});
			});

			// 댓글
			Comment comment1 = new Comment();
			comment1.setPostId(form.getPOST_ID());
			List<Comment> comments = commentRepository.commAllList(comment1);
			
			//댓글에 이미지, User 정보 넣기
			List<CommentImgForm> commentImg = comments.stream().map(comment -> comment.getFormFromComment()).collect(Collectors.toList());
			for(CommentImgForm c : commentImg) {
				User u = userRepository.findById(c.getCommWriter()).get();
				c.setUserNick(u.getUsernick());
				if(u.getAttf_id()!=null) {
					c.setATTF_OBJ(attachRepository.findById(u.getAttf_id()).get());
				}
			}
			
			form.setCOMMENT_LIST(commentImg);
			
			// 작성자 닉네임, 프로필사진 정보
			Long writer_user_id = (form.getPOST_UWRITER()==null) ? form.getPOST_WRITER() : form.getPOST_UWRITER();
			User writer = userRepository.findById(writer_user_id).get();
						
			form.setWriterNick(writer.getUsernick());
			
			if(writer.getAttf_id()!=null) {
				form.setWriterProfile(attachRepository.findById(writer.getAttf_id()).get());
			}
		});
		return postForms;
	}
	

}