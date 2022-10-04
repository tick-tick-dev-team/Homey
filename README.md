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

// Comment객체 전달하면 해당 댓글에 대한 대댓글 생성 (직접 호출 불가, 현재 setComments() 메소드 안에서 호출함
setReplyComments(comment)
```


## 파일 업로드 기능 사용 전 세팅
내 컴퓨터에 파일을 저장하기 위해 파일 저장 폴더를 미리 만들어두어야 한다.
현재 `application.properties`를 확인하면 `file.dir=D:/practice/file/`라고 적혀있다.

작성된 대로 D드라이브 밑에 /practice/file 폴더를 만들던가,
새로운 폴더를 만들어 해당 경로를 application.properties의 file.dir에 업데이트해준다.


## UserRepository의 findByNick과 findBynick의 차이점
### findByNick
중복회원을 찾기위해 사용, Optional<User> 타입을 반환, 결과가 여러개(getResultList)일수있다.
### findBynick
로그인을 하기위해 사용, User 타입을 반환, 결과가 단 하나(getSingleResult)이다.
