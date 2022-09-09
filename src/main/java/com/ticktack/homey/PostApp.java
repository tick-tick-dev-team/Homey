
package com.ticktack.homey;

import com.ticktack.homey.domain.Post;
import com.ticktack.homey.service.PostService;

public class PostApp {

	public static void main(String[] args) {
		
		SpringConfig springConfig = new SpringConfig();
		PostService postService = springConfig.postService();
		
		Long homeId = 1L;
		Post post = new Post();
		post.setPOST_HOME(homeId);
		post.setPOST_CONT("test1");
		
		Post savedPost = postService.createPost(post);
		
		System.out.println("post = #" + savedPost.getPOST_ID() + " / " + savedPost.getPOST_CONT());
	}

}

package com.ticktack.homey;

import com.ticktack.homey.domain.Post;
import com.ticktack.homey.service.PostService;

public class PostApp {

	public static void main(String[] args) {
		
		SpringConfig springConfig = new SpringConfig();
		PostService postService = springConfig.postService();
		
		Long homeId = 1L;
		Post post = new Post();
		post.setPOST_HOME(homeId);
		post.setPOST_CONT("test1");
		
		Post savedPost = postService.createPost(post);
		
		System.out.println("post = #" + savedPost.getPOST_ID() + " / " + savedPost.getPOST_CONT());
	}

}
