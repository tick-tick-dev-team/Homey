# Homey
친구들끼리 서로의 공간에 방문하여 글을 쓰며 소통할 수 있는 커뮤니티 서비스

---
## 더미데이터 삽입 클래스 사용하기
### 1. import하고 constructor로 삽입
```java
import com.ticktack.homey.dummy.DummyData;
@Controller
public class PostController {
	// 더미데이터 가져오기
	private final DummyData dummyData;
	
	public PostController(DummyData dummyData) {
		this.dummyData = dummyData;
	}
}

```

### 2. dummyData 객체의 메소드 호출하기
```java
// usernames 개수만큼 homeId 생성 -> 하나의 홈에 postContents 개수만큼 게시물 생성
// 현재 setPosts() 메소드 안에 setComments(), setAttach() 함수 호출 코드가 들어있다. -> 게시물 + 댓글 + 첨부파일 한번에 생성
dummyData.setPosts();

// Long타입 postId 전달하면 해당 게시물에 usernames 개수만큼 댓글 생성
dummyData.setComment(postId);

// postId 전달하면 해당 게시물에 1개의 첨부파일 정보 생성하고 생성된 첨부파일 id 반환
Long attf_id = dummyData.setAttach(postId);
```
