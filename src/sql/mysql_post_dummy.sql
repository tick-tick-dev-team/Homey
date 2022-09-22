# 사용자 더미

Insert into user(user_pass, user_nick, user_join) values('1234', '224', now());
Insert into user(user_pass, user_nick, user_join) values('1234', '사장', now());
Insert into user(user_pass, user_nick, user_join) values('1234', 'popdo', now());

select * from user;


# 홈 더미

Insert into home(user_Id, home_name, home_use) values(1,'224의 홈', 'Y');
Insert into home(user_Id, home_name, home_use) values(2,'사장의 홈', 'Y');
Insert into home(user_Id, home_name, home_use) values(3,'popdo의 홈', 'Y');

select * from home;


# 게시글 더미

Insert into post(post_cont, post_writer, post_date,post_uwriter, post_update, home_id) 
values('1번째게시글', 1, now(), 1, now(), 1 );
Insert into post(post_cont, post_writer, post_date,post_uwriter, post_update, home_id) 
values('2번째게시글', 1, now(), 1, now(), 1 );
Insert into post(post_cont, post_writer, post_date,post_uwriter, post_update, home_id) 
values('3번째게시글', 1, now(), 1, now(), 1 );

Insert into post(post_cont, post_writer, post_date,post_uwriter, post_update, home_id) 
values('1번째게시글', 2, now(), 2, now(), 2 );
Insert into post(post_cont, post_writer, post_date,post_uwriter, post_update, home_id) 
values('2번째게시글', 2, now(), 2, now(), 2 );
Insert into post(post_cont, post_writer, post_date,post_uwriter, post_update, home_id) 
values('3번째게시글', 2, now(), 2, now(), 2 );

Insert into post(post_cont, post_writer, post_date,post_uwriter, post_update, home_id) 
values('1번째게시글', 3, now(), 3, now(), 3 );
Insert into post(post_cont, post_writer, post_date,post_uwriter, post_update, home_id) 
values('2번째게시글', 3, now(), 3, now(), 3 );
Insert into post(post_cont, post_writer, post_date,post_uwriter, post_update, home_id) 
values('3번째게시글', 3, now(), 3, now(), 3 );

select * from post;

# 댓글 더미

INSERT INTO COMMENT(POST_ID, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(1, '첫번째 게시글의 댓글 1', 1, now(), 1, now() );
INSERT INTO COMMENT(POST_ID, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(1, '첫번째 게시글의 댓글 2', 2, now(), 2, now() );
INSERT INTO COMMENT(POST_ID, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(1, '첫번째 게시글의 댓글 3', 3, now(), 3, now() );

INSERT INTO COMMENT(POST_ID, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(2, '두번째 게시글의 댓글 1', 1, now(), 1, now() );
INSERT INTO COMMENT(POST_ID, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(2, '두번째 게시글의 댓글 2', 2, now(), 2, now() );
INSERT INTO COMMENT(POST_ID, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(2, '두번째 게시글의 댓글 3', 3, now(), 3, now() );

INSERT INTO COMMENT(POST_ID, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(3, '세번째 게시글의 댓글 1', 1, now(), 1, now() );
INSERT INTO COMMENT(POST_ID, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(3, '세번째 게시글의 댓글 2', 2, now(), 2, now() );
INSERT INTO COMMENT(POST_ID, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(3, '세번째 게시글의 댓글 3', 3, now(), 3, now() );


# 대댓글 더미

INSERT INTO COMMENT(POST_ID, comm_upid, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(1, 1,'대댓글 1', 3, now(), 3, now() );
INSERT INTO COMMENT(POST_ID, comm_upid, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(1, 1,'대댓글 2', 2, now(), 2, now() );

INSERT INTO COMMENT(POST_ID, comm_upid, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(2, 1,'대댓글 5', 1, now(), 1, now() );
INSERT INTO COMMENT(POST_ID, comm_upid, COMM_CONT, COMM_WRITER, COMM_DATE, COMM_UWRITER, COMM_UDATE) 
VALUES(2, 1,'대댓글 5', 2, now(), 2, now() );

select * from comment;