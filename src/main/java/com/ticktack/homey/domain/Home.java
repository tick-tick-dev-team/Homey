package com.ticktack.homey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

@Entity
@JsonIgnoreType
@Table(name = "home")
public class Home {
	/*HOME_ID              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '집ID',
	   USER_ID              BIGINT NOT NULL COMMENT '사용자ID',
	   HOME_NAME            VARCHAR(30) NULL COMMENT '집이름',
	   HOME_INST            VARCHAR(300) NULL COMMENT '집설명',
	   HOME_USE             CHAR(1) NOT NULL COMMENT '활성화여부',
	   HOME_THEMA           VARCHAR(36) NULL COMMENT '테마'
	   ATTF_ID              BIGINT NULL COMMENT '첨부파일ID'*/
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="home_id")
	private Long homeid;
	
	@Column(name="user_id")
	private Long userid;
	
	@Column(name="home_name")
	private String homename;
	@Column(name="home_inst")
	private String homeinst;
	@Column(name="home_use")
	private String homeuse;
	@Column(name="home_thema")
	private String homethema;
	@Column(name="attf_id")
	private Long attfid;
	
	
	public Long getHomeid() {
		return homeid;
	}
	public void setHomeid(Long homeid) {
		this.homeid = homeid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
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
	
	public Long getAttfid() {
		return attfid;
	}
	public void setAttfid(Long attfid) {
		this.attfid = attfid;
	}
	// popdo 결과값 테스트용 추가
	@Override
	public String toString() {
		return "Home [homeid=" + homeid + ", userid=" + userid + ", homename=" + homename + ", homeinst=" + homeinst
				+ ", homeuse=" + homeuse + ", homethema=" + homethema + "]";
	}
	
	
	//homes.html(main)을 위해
	public UHList getFromHome() {
		UHList mlist = new UHList();
		
		mlist.setH_ID(this.getHomeid());
		mlist.setH_NAME(this.getHomename());
		mlist.setATTF_ID(this.getAttfid());

		
		return mlist;
	}
	
	
}
