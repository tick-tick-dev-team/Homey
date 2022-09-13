package com.ticktack.homey.domain;


public class Home {
	/*HOME_ID              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '집ID',
	   USER_ID              BIGINT NOT NULL COMMENT '사용자ID',
	   HOME_NAME            VARCHAR(30) NULL COMMENT '집이름',
	   HOME_INST            VARCHAR(300) NULL COMMENT '집설명',
	   HOME_USE             CHAR(1) NOT NULL COMMENT '활성화여부',
	   HOME_THEMA           VARCHAR(36) NULL COMMENT '테마' */
	
	
	private Long homeid;
	private String userid;
	private String homename;
	private String homeinst;
	private String homeuse;
	private String homethema;
	
	public Long getHomeid() {
		return homeid;
	}
	public void setHomeid(Long homeid) {
		this.homeid = homeid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getHomename() {
		return homename;
	}
	public void setHomename(String homename) {
		this.homename = homename;
	}
	public String getHomeinst() {
		return homeinst;
	}
	public void setHomeinst(String homeinst) {
		this.homeinst = homeinst;
	}
	public String getHomeuse() {
		return homeuse;
	}
	public void setHomeuse(String homeuse) {
		this.homeuse = homeuse;
	}
	public String getHomethema() {
		return homethema;
	}
	public void setHomethema(String homethema) {
		this.homethema = homethema;
	}
}
